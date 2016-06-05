package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Precio;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.PrecioRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Precio.
 */
@RestController
@RequestMapping("/api")
public class PrecioResource {

    private final Logger log = LoggerFactory.getLogger(PrecioResource.class);

    @Inject
    private PrecioRepository precioRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /precios : Create a new precio.
     *
     * @param precio the precio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new precio, or with status 400 (Bad Request) if the precio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/precios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Precio> createPrecio(@RequestBody Precio precio) throws URISyntaxException {
        log.debug("REST request to save Precio : {}", precio);
        if (precio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("precio", "idexists", "A new precio cannot already have an ID")).body(null);
        }

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        precio.setUser(user);

        Precio result = precioRepository.save(precio);
        return ResponseEntity.created(new URI("/api/precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("precio", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /precios : Updates an existing precio.
     *
     * @param precio the precio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated precio,
     * or with status 400 (Bad Request) if the precio is not valid,
     * or with status 500 (Internal Server Error) if the precio couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/precios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Precio> updatePrecio(@RequestBody Precio precio) throws URISyntaxException {
        log.debug("REST request to update Precio : {}", precio);
        if (precio.getId() == null) {
            return createPrecio(precio);
        }
        Precio result = precioRepository.save(precio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("precio", precio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /precios : get all the precios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of precios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/precios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Precio>> getAllPrecios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Precios");
        Page<Precio> page = precioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/precios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /precios/:id : get the "id" precio.
     *
     * @param id the id of the precio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the precio, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/precios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Precio> getPrecio(@PathVariable Long id) {
        log.debug("REST request to get Precio : {}", id);
        Precio precio = precioRepository.findOne(id);
        return Optional.ofNullable(precio)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /precios/:id : delete the "id" precio.
     *
     * @param id the id of the precio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/precios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrecio(@PathVariable Long id) {
        log.debug("REST request to delete Precio : {}", id);
        precioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("precio", id.toString())).build();
    }

}
