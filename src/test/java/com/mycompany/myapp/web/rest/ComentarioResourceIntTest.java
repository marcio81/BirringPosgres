package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Comentario;
import com.mycompany.myapp.repository.ComentarioRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ComentarioResource REST controller.
 *
 * @see ComentarioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class ComentarioResourceIntTest {

    private static final String DEFAULT_COMENTARIO = "AAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBB";

    @Inject
    private ComentarioRepository comentarioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restComentarioMockMvc;

    private Comentario comentario;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComentarioResource comentarioResource = new ComentarioResource();
        ReflectionTestUtils.setField(comentarioResource, "comentarioRepository", comentarioRepository);
        this.restComentarioMockMvc = MockMvcBuilders.standaloneSetup(comentarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        comentario = new Comentario();
        comentario.setComentario(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createComentario() throws Exception {
        int databaseSizeBeforeCreate = comentarioRepository.findAll().size();

        // Create the Comentario

        restComentarioMockMvc.perform(post("/api/comentarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comentario)))
                .andExpect(status().isCreated());

        // Validate the Comentario in the database
        List<Comentario> comentarios = comentarioRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeCreate + 1);
        Comentario testComentario = comentarios.get(comentarios.size() - 1);
        assertThat(testComentario.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get all the comentarios
        restComentarioMockMvc.perform(get("/api/comentarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(comentario.getId().intValue())))
                .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);

        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", comentario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(comentario.getId().intValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComentario() throws Exception {
        // Get the comentario
        restComentarioMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        int databaseSizeBeforeUpdate = comentarioRepository.findAll().size();

        // Update the comentario
        Comentario updatedComentario = new Comentario();
        updatedComentario.setId(comentario.getId());
        updatedComentario.setComentario(UPDATED_COMENTARIO);

        restComentarioMockMvc.perform(put("/api/comentarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedComentario)))
                .andExpect(status().isOk());

        // Validate the Comentario in the database
        List<Comentario> comentarios = comentarioRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeUpdate);
        Comentario testComentario = comentarios.get(comentarios.size() - 1);
        assertThat(testComentario.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void deleteComentario() throws Exception {
        // Initialize the database
        comentarioRepository.saveAndFlush(comentario);
        int databaseSizeBeforeDelete = comentarioRepository.findAll().size();

        // Get the comentario
        restComentarioMockMvc.perform(delete("/api/comentarios/{id}", comentario.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Comentario> comentarios = comentarioRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
