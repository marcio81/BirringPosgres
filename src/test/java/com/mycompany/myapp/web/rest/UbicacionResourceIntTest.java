package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Ubicacion;
import com.mycompany.myapp.repository.UbicacionRepository;

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
 * Test class for the UbicacionResource REST controller.
 *
 * @see UbicacionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class UbicacionResourceIntTest {

    private static final String DEFAULT_UBI_NAME = "AAAAA";
    private static final String UPDATED_UBI_NAME = "BBBBB";
    private static final String DEFAULT_DIRECCION = "AAAAA";
    private static final String UPDATED_DIRECCION = "BBBBB";

    private static final Float DEFAULT_LONGITUD = 1F;
    private static final Float UPDATED_LONGITUD = 2F;

    private static final Float DEFAULT_LATITUD = 1F;
    private static final Float UPDATED_LATITUD = 2F;

    @Inject
    private UbicacionRepository ubicacionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUbicacionMockMvc;

    private Ubicacion ubicacion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UbicacionResource ubicacionResource = new UbicacionResource();
        ReflectionTestUtils.setField(ubicacionResource, "ubicacionRepository", ubicacionRepository);
        this.restUbicacionMockMvc = MockMvcBuilders.standaloneSetup(ubicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ubicacion = new Ubicacion();
        ubicacion.setUbiName(DEFAULT_UBI_NAME);
        ubicacion.setDireccion(DEFAULT_DIRECCION);
        ubicacion.setLongitud(DEFAULT_LONGITUD);
        ubicacion.setLatitud(DEFAULT_LATITUD);
    }

    @Test
    @Transactional
    public void createUbicacion() throws Exception {
        int databaseSizeBeforeCreate = ubicacionRepository.findAll().size();

        // Create the Ubicacion

        restUbicacionMockMvc.perform(post("/api/ubicacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ubicacion)))
                .andExpect(status().isCreated());

        // Validate the Ubicacion in the database
        List<Ubicacion> ubicacions = ubicacionRepository.findAll();
        assertThat(ubicacions).hasSize(databaseSizeBeforeCreate + 1);
        Ubicacion testUbicacion = ubicacions.get(ubicacions.size() - 1);
        assertThat(testUbicacion.getUbiName()).isEqualTo(DEFAULT_UBI_NAME);
        assertThat(testUbicacion.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testUbicacion.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testUbicacion.getLatitud()).isEqualTo(DEFAULT_LATITUD);
    }

    @Test
    @Transactional
    public void getAllUbicacions() throws Exception {
        // Initialize the database
        ubicacionRepository.saveAndFlush(ubicacion);

        // Get all the ubicacions
        restUbicacionMockMvc.perform(get("/api/ubicacions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ubicacion.getId().intValue())))
                .andExpect(jsonPath("$.[*].ubiName").value(hasItem(DEFAULT_UBI_NAME.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD.doubleValue())))
                .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD.doubleValue())));
    }

    @Test
    @Transactional
    public void getUbicacion() throws Exception {
        // Initialize the database
        ubicacionRepository.saveAndFlush(ubicacion);

        // Get the ubicacion
        restUbicacionMockMvc.perform(get("/api/ubicacions/{id}", ubicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ubicacion.getId().intValue()))
            .andExpect(jsonPath("$.ubiName").value(DEFAULT_UBI_NAME.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD.doubleValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUbicacion() throws Exception {
        // Get the ubicacion
        restUbicacionMockMvc.perform(get("/api/ubicacions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUbicacion() throws Exception {
        // Initialize the database
        ubicacionRepository.saveAndFlush(ubicacion);
        int databaseSizeBeforeUpdate = ubicacionRepository.findAll().size();

        // Update the ubicacion
        Ubicacion updatedUbicacion = new Ubicacion();
        updatedUbicacion.setId(ubicacion.getId());
        updatedUbicacion.setUbiName(UPDATED_UBI_NAME);
        updatedUbicacion.setDireccion(UPDATED_DIRECCION);
        updatedUbicacion.setLongitud(UPDATED_LONGITUD);
        updatedUbicacion.setLatitud(UPDATED_LATITUD);

        restUbicacionMockMvc.perform(put("/api/ubicacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedUbicacion)))
                .andExpect(status().isOk());

        // Validate the Ubicacion in the database
        List<Ubicacion> ubicacions = ubicacionRepository.findAll();
        assertThat(ubicacions).hasSize(databaseSizeBeforeUpdate);
        Ubicacion testUbicacion = ubicacions.get(ubicacions.size() - 1);
        assertThat(testUbicacion.getUbiName()).isEqualTo(UPDATED_UBI_NAME);
        assertThat(testUbicacion.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testUbicacion.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testUbicacion.getLatitud()).isEqualTo(UPDATED_LATITUD);
    }

    @Test
    @Transactional
    public void deleteUbicacion() throws Exception {
        // Initialize the database
        ubicacionRepository.saveAndFlush(ubicacion);
        int databaseSizeBeforeDelete = ubicacionRepository.findAll().size();

        // Get the ubicacion
        restUbicacionMockMvc.perform(delete("/api/ubicacions/{id}", ubicacion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ubicacion> ubicacions = ubicacionRepository.findAll();
        assertThat(ubicacions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
