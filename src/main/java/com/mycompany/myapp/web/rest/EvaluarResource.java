package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Evaluar;
import com.mycompany.myapp.repository.EvaluarRepository;
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
 * REST controller for managing Evaluar.
 */
@RestController
@RequestMapping("/api")
public class EvaluarResource {

    private final Logger log = LoggerFactory.getLogger(EvaluarResource.class);

    @Inject
    private EvaluarRepository evaluarRepository;

    /**
     * POST  /evaluars : Create a new evaluar.
     *
     * @param evaluar the evaluar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluar, or with status 400 (Bad Request) if the evaluar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/evaluars",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluar> createEvaluar(@RequestBody Evaluar evaluar) throws URISyntaxException {
        log.debug("REST request to save Evaluar : {}", evaluar);
        if (evaluar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("evaluar", "idexists", "A new evaluar cannot already have an ID")).body(null);
        }
        Evaluar result = evaluarRepository.save(evaluar);
        return ResponseEntity.created(new URI("/api/evaluars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("evaluar", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluars : Updates an existing evaluar.
     *
     * @param evaluar the evaluar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluar,
     * or with status 400 (Bad Request) if the evaluar is not valid,
     * or with status 500 (Internal Server Error) if the evaluar couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/evaluars",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluar> updateEvaluar(@RequestBody Evaluar evaluar) throws URISyntaxException {
        log.debug("REST request to update Evaluar : {}", evaluar);
        if (evaluar.getId() == null) {
            return createEvaluar(evaluar);
        }
        Evaluar result = evaluarRepository.save(evaluar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("evaluar", evaluar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluars : get all the evaluars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of evaluars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/evaluars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Evaluar>> getAllEvaluars(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Evaluars");

        /*List<Object[]> top10 = evaluarRepository.findTop10();

        top10.forEach((result) ->  System.out.println("media = " + result[0] + "idCerveza = " + result[1]));*/

        Page<Evaluar> page = evaluarRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluars/:id : get the "id" evaluar.
     *
     * @param id the id of the evaluar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluar, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/evaluars/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Evaluar> getEvaluar(@PathVariable Long id) {
        log.debug("REST request to get Evaluar : {}", id);
        Evaluar evaluar = evaluarRepository.findOne(id);
        return Optional.ofNullable(evaluar)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /evaluars/:id : delete the "id" evaluar.
     *
     * @param id the id of the evaluar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/evaluars/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvaluar(@PathVariable Long id) {
        log.debug("REST request to delete Evaluar : {}", id);
        evaluarRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("evaluar", id.toString())).build();
    }

}
