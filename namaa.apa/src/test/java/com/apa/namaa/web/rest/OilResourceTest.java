package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.Oil;
import com.apa.namaa.repository.OilRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
 * Test class for the OilResource REST controller.
 *
 * @see OilResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OilResourceTest {


    private static final Double DEFAULT_SESAME = 1D;
    private static final Double UPDATED_SESAME = 2D;

    private static final Double DEFAULT_SAFFLOWER = 1D;
    private static final Double UPDATED_SAFFLOWER = 2D;

    private static final Double DEFAULT_RADISH = 1D;
    private static final Double UPDATED_RADISH = 2D;

    @Inject
    private OilRepository oilRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOilMockMvc;

    private Oil oil;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OilResource oilResource = new OilResource();
        ReflectionTestUtils.setField(oilResource, "oilRepository", oilRepository);
        this.restOilMockMvc = MockMvcBuilders.standaloneSetup(oilResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        oil = new Oil();
        oil.setSesame(DEFAULT_SESAME);
        oil.setSafflower(DEFAULT_SAFFLOWER);
        oil.setRadish(DEFAULT_RADISH);
    }

    @Test
    @Transactional
    public void createOil() throws Exception {
        int databaseSizeBeforeCreate = oilRepository.findAll().size();

        // Create the Oil

        restOilMockMvc.perform(post("/api/oils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oil)))
                .andExpect(status().isCreated());

        // Validate the Oil in the database
        List<Oil> oils = oilRepository.findAll();
        assertThat(oils).hasSize(databaseSizeBeforeCreate + 1);
        Oil testOil = oils.get(oils.size() - 1);
        assertThat(testOil.getSesame()).isEqualTo(DEFAULT_SESAME);
        assertThat(testOil.getSafflower()).isEqualTo(DEFAULT_SAFFLOWER);
        assertThat(testOil.getRadish()).isEqualTo(DEFAULT_RADISH);
    }

    @Test
    @Transactional
    public void getAllOils() throws Exception {
        // Initialize the database
        oilRepository.saveAndFlush(oil);

        // Get all the oils
        restOilMockMvc.perform(get("/api/oils"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(oil.getId().intValue())))
                .andExpect(jsonPath("$.[*].sesame").value(hasItem(DEFAULT_SESAME.doubleValue())))
                .andExpect(jsonPath("$.[*].safflower").value(hasItem(DEFAULT_SAFFLOWER.doubleValue())))
                .andExpect(jsonPath("$.[*].radish").value(hasItem(DEFAULT_RADISH.doubleValue())));
    }

    @Test
    @Transactional
    public void getOil() throws Exception {
        // Initialize the database
        oilRepository.saveAndFlush(oil);

        // Get the oil
        restOilMockMvc.perform(get("/api/oils/{id}", oil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(oil.getId().intValue()))
            .andExpect(jsonPath("$.sesame").value(DEFAULT_SESAME.doubleValue()))
            .andExpect(jsonPath("$.safflower").value(DEFAULT_SAFFLOWER.doubleValue()))
            .andExpect(jsonPath("$.radish").value(DEFAULT_RADISH.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOil() throws Exception {
        // Get the oil
        restOilMockMvc.perform(get("/api/oils/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOil() throws Exception {
        // Initialize the database
        oilRepository.saveAndFlush(oil);

		int databaseSizeBeforeUpdate = oilRepository.findAll().size();

        // Update the oil
        oil.setSesame(UPDATED_SESAME);
        oil.setSafflower(UPDATED_SAFFLOWER);
        oil.setRadish(UPDATED_RADISH);
        

        restOilMockMvc.perform(put("/api/oils")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(oil)))
                .andExpect(status().isOk());

        // Validate the Oil in the database
        List<Oil> oils = oilRepository.findAll();
        assertThat(oils).hasSize(databaseSizeBeforeUpdate);
        Oil testOil = oils.get(oils.size() - 1);
        assertThat(testOil.getSesame()).isEqualTo(UPDATED_SESAME);
        assertThat(testOil.getSafflower()).isEqualTo(UPDATED_SAFFLOWER);
        assertThat(testOil.getRadish()).isEqualTo(UPDATED_RADISH);
    }

    @Test
    @Transactional
    public void deleteOil() throws Exception {
        // Initialize the database
        oilRepository.saveAndFlush(oil);

		int databaseSizeBeforeDelete = oilRepository.findAll().size();

        // Get the oil
        restOilMockMvc.perform(delete("/api/oils/{id}", oil.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Oil> oils = oilRepository.findAll();
        assertThat(oils).hasSize(databaseSizeBeforeDelete - 1);
    }
}
