package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cervesa;
import com.mycompany.myapp.domain.Comentario;
import com.mycompany.myapp.domain.Precio;
import com.mycompany.myapp.repository.CervesaRepository;
import com.mycompany.myapp.repository.ComentarioRepository;
import com.mycompany.myapp.repository.EvaluarRepository;
import com.mycompany.myapp.repository.PrecioRepository;
import com.mycompany.myapp.web.rest.dto.CervezaDTO;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cervesa.
 */
@RestController
@RequestMapping("/api")
public class CervesaResource {

    private final Logger log = LoggerFactory.getLogger(CervesaResource.class);

    @Inject
    private CervesaRepository cervesaRepository;

    @Inject
    private EvaluarRepository evaluarRepository;

    @Inject
    private PrecioRepository precioRepository;

    @Inject
    private ComentarioRepository comentarioRepository;

    /**
     * POST  /cervesas : Create a new cervesa.
     *
     * @param cervesa the cervesa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cervesa, or with status 400 (Bad Request) if the cervesa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cervesas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cervesa> createCervesa(@RequestBody Cervesa cervesa) throws URISyntaxException {
        log.debug("REST request to save Cervesa : {}", cervesa);
        if (cervesa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cervesa", "idexists", "A new cervesa cannot already have an ID")).body(null);
        }
        Cervesa result = cervesaRepository.save(cervesa);
        return ResponseEntity.created(new URI("/api/cervesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cervesa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cervesas : Updates an existing cervesa.
     *
     * @param cervesa the cervesa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cervesa,
     * or with status 400 (Bad Request) if the cervesa is not valid,
     * or with status 500 (Internal Server Error) if the cervesa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cervesas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cervesa> updateCervesa(@RequestBody Cervesa cervesa) throws URISyntaxException {
        log.debug("REST request to update Cervesa : {}", cervesa);
        if (cervesa.getId() == null) {
            return createCervesa(cervesa);
        }
        Cervesa result = cervesaRepository.save(cervesa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cervesa", cervesa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cervesas : get all the cervesas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cervesas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cervesas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cervesa>> getAllCervesas(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cervesas");
        Page<Cervesa> page = cervesaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cervesas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cervesas/:id : get the "id" cervesa.
     *
     * @param id the id of the cervesa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cervesa, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cervesas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cervesa> getCervesa(@PathVariable Long id) {
        log.debug("REST request to get Cervesa : {}", id);
        Cervesa cervesa = cervesaRepository.findOne(id);
        return Optional.ofNullable(cervesa)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cervesas/:id : delete the "id" cervesa.
     *
     * @param id the id of the cervesa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cervesas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCervesa(@PathVariable Long id) {
        log.debug("REST request to delete Cervesa : {}", id);
        cervesaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cervesa", id.toString())).build();
    }

    /*TOP 10*/
    @RequestMapping(value = "/topcervesas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CervezaDTO>> getTopCervesas(Pageable pageable)
        throws URISyntaxException {
        Pageable topTen = new PageRequest(0, 10);
        log.debug("REST request to get a page of Cervesas");

        Page<Object[]> page = evaluarRepository.findTop10(topTen);

        List<CervezaDTO> cervezaDTOs = new ArrayList<>();

        page.getContent()
            .forEach(cerveza -> cervezaDTOs.add(new CervezaDTO((Long) cerveza[0], (String) cerveza[1], (byte[]) cerveza[2], (Double) cerveza[3])));


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/topcervesas");
        return new ResponseEntity<>(cervezaDTOs, headers, HttpStatus.OK);
    }

    //COMENTARIO VICTOR utiliza lo mismo que el cerveza-detail /cervesas/{id}

 /*   @RequestMapping(value = "/coment/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Comentario>> comentariosIDcerveza(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cervesas");
        List<Comentario> comentario = comentarioRepository.findComentarioID(id);
        return new ResponseEntity<>(comentario, HttpStatus.OK);
    }*/
// BUSCADOR
    @RequestMapping(value = "/buscacervesas/{cervesaName}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cervesa>> getBuCervesas(@PathVariable String cervesaName)
        throws URISyntaxException {
        log.debug("REST request to get Cervesa: {}", cervesaName);
        List<Cervesa> cerva = cervesaRepository.findAllCerva(cervesaName);

        return new ResponseEntity<>(cerva, HttpStatus.OK);
    }

    // Comentario
    @RequestMapping(value = "/cervesas/{id}/comentarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Comentario>> getComentariosCervezaID(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get Comentario: {}", id);
        List<Comentario> coment = comentarioRepository.findComentarioID(id);

        return new ResponseEntity<>(coment, HttpStatus.OK);
    }

    // Precio medio
    @RequestMapping(value = "/cervesas/{id}/precioMedio",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Precio>> getPrecioMedioCervezaID(@PathVariable Long id)
        throws URISyntaxException {
        log.debug("REST request to get Comentario: {}", id);
        List<Precio> prec = precioRepository.findPrecioMedio(id);

        return new ResponseEntity<>(prec, HttpStatus.OK);
    }
}

