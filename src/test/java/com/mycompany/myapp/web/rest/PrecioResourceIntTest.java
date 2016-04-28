package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Precio;
import com.mycompany.myapp.repository.PrecioRepository;

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
 * Test class for the PrecioResource REST controller.
 *
 * @see PrecioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class PrecioResourceIntTest {


    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Inject
    private PrecioRepository precioRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrecioMockMvc;

    private Precio precio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrecioResource precioResource = new PrecioResource();
        ReflectionTestUtils.setField(precioResource, "precioRepository", precioRepository);
        this.restPrecioMockMvc = MockMvcBuilders.standaloneSetup(precioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        precio = new Precio();
        precio.setPrecio(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createPrecio() throws Exception {
        int databaseSizeBeforeCreate = precioRepository.findAll().size();

        // Create the Precio

        restPrecioMockMvc.perform(post("/api/precios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(precio)))
                .andExpect(status().isCreated());

        // Validate the Precio in the database
        List<Precio> precios = precioRepository.findAll();
        assertThat(precios).hasSize(databaseSizeBeforeCreate + 1);
        Precio testPrecio = precios.get(precios.size() - 1);
        assertThat(testPrecio.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void getAllPrecios() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get all the precios
        restPrecioMockMvc.perform(get("/api/precios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(precio.getId().intValue())))
                .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getPrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);

        // Get the precio
        restPrecioMockMvc.perform(get("/api/precios/{id}", precio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(precio.getId().intValue()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrecio() throws Exception {
        // Get the precio
        restPrecioMockMvc.perform(get("/api/precios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);
        int databaseSizeBeforeUpdate = precioRepository.findAll().size();

        // Update the precio
        Precio updatedPrecio = new Precio();
        updatedPrecio.setId(precio.getId());
        updatedPrecio.setPrecio(UPDATED_PRECIO);

        restPrecioMockMvc.perform(put("/api/precios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPrecio)))
                .andExpect(status().isOk());

        // Validate the Precio in the database
        List<Precio> precios = precioRepository.findAll();
        assertThat(precios).hasSize(databaseSizeBeforeUpdate);
        Precio testPrecio = precios.get(precios.size() - 1);
        assertThat(testPrecio.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void deletePrecio() throws Exception {
        // Initialize the database
        precioRepository.saveAndFlush(precio);
        int databaseSizeBeforeDelete = precioRepository.findAll().size();

        // Get the precio
        restPrecioMockMvc.perform(delete("/api/precios/{id}", precio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Precio> precios = precioRepository.findAll();
        assertThat(precios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
