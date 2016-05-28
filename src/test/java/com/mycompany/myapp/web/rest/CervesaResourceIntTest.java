package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Cervesa;
import com.mycompany.myapp.repository.CervesaRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CervesaResource REST controller.
 *
 * @see CervesaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JhipsterApp.class)
@WebAppConfiguration
@IntegrationTest
public class CervesaResourceIntTest {

    private static final String DEFAULT_CERVESA_NAME = "AAAAA";
    private static final String UPDATED_CERVESA_NAME = "BBBBB";
    private static final String DEFAULT_TIPO = "AAAAA";
    private static final String UPDATED_TIPO = "BBBBB";
    private static final String DEFAULT_FABRICANTE = "AAAAA";
    private static final String UPDATED_FABRICANTE = "BBBBB";
    private static final String DEFAULT_PAIS = "AAAAA";
    private static final String UPDATED_PAIS = "BBBBB";

    private static final Double DEFAULT_GRADUACION = 1D;
    private static final Double UPDATED_GRADUACION = 2D;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    @Inject
    private CervesaRepository cervesaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCervesaMockMvc;

    private Cervesa cervesa;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CervesaResource cervesaResource = new CervesaResource();
        ReflectionTestUtils.setField(cervesaResource, "cervesaRepository", cervesaRepository);
        this.restCervesaMockMvc = MockMvcBuilders.standaloneSetup(cervesaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cervesa = new Cervesa();
        cervesa.setCervesaName(DEFAULT_CERVESA_NAME);
        cervesa.setTipo(DEFAULT_TIPO);
        cervesa.setFabricante(DEFAULT_FABRICANTE);
        cervesa.setPais(DEFAULT_PAIS);
        cervesa.setGraduacion(DEFAULT_GRADUACION);
        cervesa.setFoto(DEFAULT_FOTO);
        cervesa.setFotoContentType(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCervesa() throws Exception {
        int databaseSizeBeforeCreate = cervesaRepository.findAll().size();

        // Create the Cervesa

        restCervesaMockMvc.perform(post("/api/cervesas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cervesa)))
                .andExpect(status().isCreated());

        // Validate the Cervesa in the database
        List<Cervesa> cervesas = cervesaRepository.findAll();
        assertThat(cervesas).hasSize(databaseSizeBeforeCreate + 1);
        Cervesa testCervesa = cervesas.get(cervesas.size() - 1);
        assertThat(testCervesa.getCervesaName()).isEqualTo(DEFAULT_CERVESA_NAME);
        assertThat(testCervesa.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCervesa.getFabricante()).isEqualTo(DEFAULT_FABRICANTE);
        assertThat(testCervesa.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testCervesa.getGraduacion()).isEqualTo(DEFAULT_GRADUACION);
        assertThat(testCervesa.getFoto()).isEqualTo(DEFAULT_FOTO);
       // assertThat(testCervesa.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCervesas() throws Exception {
        // Initialize the database
        cervesaRepository.saveAndFlush(cervesa);

        // Get all the cervesas
        restCervesaMockMvc.perform(get("/api/cervesas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cervesa.getId().intValue())))
                .andExpect(jsonPath("$.[*].cervesaName").value(hasItem(DEFAULT_CERVESA_NAME.toString())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
                .andExpect(jsonPath("$.[*].fabricante").value(hasItem(DEFAULT_FABRICANTE.toString())))
                .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
                .andExpect(jsonPath("$.[*].graduacion").value(hasItem(DEFAULT_GRADUACION.doubleValue())))
                .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))));
    }

    @Test
    @Transactional
    public void getCervesa() throws Exception {
        // Initialize the database
        cervesaRepository.saveAndFlush(cervesa);

        // Get the cervesa
        restCervesaMockMvc.perform(get("/api/cervesas/{id}", cervesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cervesa.getId().intValue()))
            .andExpect(jsonPath("$.cervesaName").value(DEFAULT_CERVESA_NAME.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.fabricante").value(DEFAULT_FABRICANTE.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.graduacion").value(DEFAULT_GRADUACION.doubleValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingCervesa() throws Exception {
        // Get the cervesa
        restCervesaMockMvc.perform(get("/api/cervesas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCervesa() throws Exception {
        // Initialize the database
        cervesaRepository.saveAndFlush(cervesa);
        int databaseSizeBeforeUpdate = cervesaRepository.findAll().size();

        // Update the cervesa
        Cervesa updatedCervesa = new Cervesa();
        updatedCervesa.setId(cervesa.getId());
        updatedCervesa.setCervesaName(UPDATED_CERVESA_NAME);
        updatedCervesa.setTipo(UPDATED_TIPO);
        updatedCervesa.setFabricante(UPDATED_FABRICANTE);
        updatedCervesa.setPais(UPDATED_PAIS);
        updatedCervesa.setGraduacion(UPDATED_GRADUACION);
        updatedCervesa.setFoto(UPDATED_FOTO);
        updatedCervesa.setFotoContentType(UPDATED_FOTO_CONTENT_TYPE);

        restCervesaMockMvc.perform(put("/api/cervesas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCervesa)))
                .andExpect(status().isOk());

        // Validate the Cervesa in the database
        List<Cervesa> cervesas = cervesaRepository.findAll();
        assertThat(cervesas).hasSize(databaseSizeBeforeUpdate);
        Cervesa testCervesa = cervesas.get(cervesas.size() - 1);
        assertThat(testCervesa.getCervesaName()).isEqualTo(UPDATED_CERVESA_NAME);
        assertThat(testCervesa.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCervesa.getFabricante()).isEqualTo(UPDATED_FABRICANTE);
        assertThat(testCervesa.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testCervesa.getGraduacion()).isEqualTo(UPDATED_GRADUACION);
        assertThat(testCervesa.getFoto()).isEqualTo(UPDATED_FOTO);
      //  assertThat(testCervesa.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteCervesa() throws Exception {
        // Initialize the database
        cervesaRepository.saveAndFlush(cervesa);
        int databaseSizeBeforeDelete = cervesaRepository.findAll().size();

        // Get the cervesa
        restCervesaMockMvc.perform(delete("/api/cervesas/{id}", cervesa.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cervesa> cervesas = cervesaRepository.findAll();
        assertThat(cervesas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
