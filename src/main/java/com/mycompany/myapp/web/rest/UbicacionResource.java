package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cervesa;
import com.mycompany.myapp.domain.Precio;
import com.mycompany.myapp.domain.Ubicacion;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.CervesaRepository;
import com.mycompany.myapp.repository.PrecioRepository;
import com.mycompany.myapp.repository.UbicacionRepository;
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
import java.util.Set;

/**
 * REST controller for managing Ubicacion.
 */
@RestController
@RequestMapping("/api")
public class UbicacionResource {

    private final Logger log = LoggerFactory.getLogger(UbicacionResource.class);

    @Inject
    private UbicacionRepository ubicacionRepository;

    @Inject
    private PrecioRepository precioRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private CervesaRepository cervesaRepository;

    /**
     * POST  /ubicacions : Create a new ubicacion.
     */
    @RequestMapping(value = "/ubicacions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ubicacion> createUbicacion(@RequestBody Ubicacion ubicacion ) throws URISyntaxException {
        log.debug("REST request to save Ubicacion : {}", ubicacion);
        if (ubicacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ubicacion", "idexists", "A new ubicacion cannot already have an ID")).body(null);
        }
        Ubicacion result = ubicacionRepository.save(ubicacion);
        return ResponseEntity.created(new URI("/api/ubicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ubicacion", result.getId().toString()))
            .body(result);
    }




    /**
     * PUT  /ubicacions : Updates an existing ubicacion.
     *
     * @param ubicacion the ubicacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ubicacion,
     * or with status 400 (Bad Request) if the ubicacion is not valid,
     * or with status 500 (Internal Server Error) if the ubicacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/ubicacions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ubicacion> updateUbicacion(@RequestBody Ubicacion ubicacion) throws URISyntaxException {
        log.debug("REST request to update Ubicacion : {}", ubicacion);
        if (ubicacion.getId() == null) {
            return createUbicacion(ubicacion);
        }
        Ubicacion result = ubicacionRepository.save(ubicacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ubicacion", ubicacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ubicacions : get all the ubicacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ubicacions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/ubicacions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Ubicacion>> getAllUbicacions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Ubicacions");
        Page<Ubicacion> page = ubicacionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ubicacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ubicacions/:id : get the "id" ubicacion.
     *
     * @param id the id of the ubicacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ubicacion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/ubicacions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Ubicacion> getUbicacion(@PathVariable Long id) {
        log.debug("REST request to get Ubicacion : {}", id);
        Ubicacion ubicacion = ubicacionRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(ubicacion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ubicacions/:id : delete the "id" ubicacion.
     *
     * @param id the id of the ubicacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/ubicacions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUbicacion(@PathVariable Long id) {
        log.debug("REST request to delete Ubicacion : {}", id);
        ubicacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ubicacion", id.toString())).build();
    }

}
