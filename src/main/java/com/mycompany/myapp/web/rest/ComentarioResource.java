package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Comentario;
import com.mycompany.myapp.repository.ComentarioRepository;
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
 * REST controller for managing Comentario.
 */
@RestController
@RequestMapping("/api")
public class ComentarioResource {

    private final Logger log = LoggerFactory.getLogger(ComentarioResource.class);
        
    @Inject
    private ComentarioRepository comentarioRepository;
    
    /**
     * POST  /comentarios : Create a new comentario.
     *
     * @param comentario the comentario to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentario, or with status 400 (Bad Request) if the comentario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/comentarios",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comentario> createComentario(@RequestBody Comentario comentario) throws URISyntaxException {
        log.debug("REST request to save Comentario : {}", comentario);
        if (comentario.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comentario", "idexists", "A new comentario cannot already have an ID")).body(null);
        }
        Comentario result = comentarioRepository.save(comentario);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comentario", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentarios : Updates an existing comentario.
     *
     * @param comentario the comentario to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentario,
     * or with status 400 (Bad Request) if the comentario is not valid,
     * or with status 500 (Internal Server Error) if the comentario couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/comentarios",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comentario> updateComentario(@RequestBody Comentario comentario) throws URISyntaxException {
        log.debug("REST request to update Comentario : {}", comentario);
        if (comentario.getId() == null) {
            return createComentario(comentario);
        }
        Comentario result = comentarioRepository.save(comentario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comentario", comentario.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentarios : get all the comentarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comentarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/comentarios",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Comentario>> getAllComentarios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Comentarios");
        Page<Comentario> page = comentarioRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comentarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comentarios/:id : get the "id" comentario.
     *
     * @param id the id of the comentario to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentario, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/comentarios/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Comentario> getComentario(@PathVariable Long id) {
        log.debug("REST request to get Comentario : {}", id);
        Comentario comentario = comentarioRepository.findOne(id);
        return Optional.ofNullable(comentario)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comentarios/:id : delete the "id" comentario.
     *
     * @param id the id of the comentario to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/comentarios/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        log.debug("REST request to delete Comentario : {}", id);
        comentarioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comentario", id.toString())).build();
    }

}
