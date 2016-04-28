package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Evaluar;
import com.mycompany.myapp.repository.EvaluarRepository;

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
 * Test class for the EvaluarResource REST controller.
 *
 * @see EvaluarResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class EvaluarResourceIntTest {


    private static final Integer DEFAULT_EVALUACION = 1;
    private static final Integer UPDATED_EVALUACION = 2;

    @Inject
    private EvaluarRepository evaluarRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEvaluarMockMvc;

    private Evaluar evaluar;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluarResource evaluarResource = new EvaluarResource();
        ReflectionTestUtils.setField(evaluarResource, "evaluarRepository", evaluarRepository);
        this.restEvaluarMockMvc = MockMvcBuilders.standaloneSetup(evaluarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        evaluar = new Evaluar();
        evaluar.setEvaluacion(DEFAULT_EVALUACION);
    }

    @Test
    @Transactional
    public void createEvaluar() throws Exception {
        int databaseSizeBeforeCreate = evaluarRepository.findAll().size();

        // Create the Evaluar

        restEvaluarMockMvc.perform(post("/api/evaluars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(evaluar)))
                .andExpect(status().isCreated());

        // Validate the Evaluar in the database
        List<Evaluar> evaluars = evaluarRepository.findAll();
        assertThat(evaluars).hasSize(databaseSizeBeforeCreate + 1);
        Evaluar testEvaluar = evaluars.get(evaluars.size() - 1);
        assertThat(testEvaluar.getEvaluacion()).isEqualTo(DEFAULT_EVALUACION);
    }

    @Test
    @Transactional
    public void getAllEvaluars() throws Exception {
        // Initialize the database
        evaluarRepository.saveAndFlush(evaluar);

        // Get all the evaluars
        restEvaluarMockMvc.perform(get("/api/evaluars?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(evaluar.getId().intValue())))
                .andExpect(jsonPath("$.[*].evaluacion").value(hasItem(DEFAULT_EVALUACION)));
    }

    @Test
    @Transactional
    public void getEvaluar() throws Exception {
        // Initialize the database
        evaluarRepository.saveAndFlush(evaluar);

        // Get the evaluar
        restEvaluarMockMvc.perform(get("/api/evaluars/{id}", evaluar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(evaluar.getId().intValue()))
            .andExpect(jsonPath("$.evaluacion").value(DEFAULT_EVALUACION));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluar() throws Exception {
        // Get the evaluar
        restEvaluarMockMvc.perform(get("/api/evaluars/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluar() throws Exception {
        // Initialize the database
        evaluarRepository.saveAndFlush(evaluar);
        int databaseSizeBeforeUpdate = evaluarRepository.findAll().size();

        // Update the evaluar
        Evaluar updatedEvaluar = new Evaluar();
        updatedEvaluar.setId(evaluar.getId());
        updatedEvaluar.setEvaluacion(UPDATED_EVALUACION);

        restEvaluarMockMvc.perform(put("/api/evaluars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEvaluar)))
                .andExpect(status().isOk());

        // Validate the Evaluar in the database
        List<Evaluar> evaluars = evaluarRepository.findAll();
        assertThat(evaluars).hasSize(databaseSizeBeforeUpdate);
        Evaluar testEvaluar = evaluars.get(evaluars.size() - 1);
        assertThat(testEvaluar.getEvaluacion()).isEqualTo(UPDATED_EVALUACION);
    }

    @Test
    @Transactional
    public void deleteEvaluar() throws Exception {
        // Initialize the database
        evaluarRepository.saveAndFlush(evaluar);
        int databaseSizeBeforeDelete = evaluarRepository.findAll().size();

        // Get the evaluar
        restEvaluarMockMvc.perform(delete("/api/evaluars/{id}", evaluar.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Evaluar> evaluars = evaluarRepository.findAll();
        assertThat(evaluars).hasSize(databaseSizeBeforeDelete - 1);
    }
}
