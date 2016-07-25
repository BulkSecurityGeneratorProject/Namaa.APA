package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.GrainType2;
import com.apa.namaa.repository.GrainType2Repository;

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
 * Test class for the GrainType2Resource REST controller.
 *
 * @see GrainType2Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GrainType2ResourceTest {


    private static final Double DEFAULT_WHEAT = 1D;
    private static final Double UPDATED_WHEAT = 2D;

    private static final Double DEFAULT_BARLEY = 1D;
    private static final Double UPDATED_BARLEY = 2D;

    @Inject
    private GrainType2Repository grainType2Repository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restGrainType2MockMvc;

    private GrainType2 grainType2;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrainType2Resource grainType2Resource = new GrainType2Resource();
        ReflectionTestUtils.setField(grainType2Resource, "grainType2Repository", grainType2Repository);
        this.restGrainType2MockMvc = MockMvcBuilders.standaloneSetup(grainType2Resource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grainType2 = new GrainType2();
        grainType2.setWheat(DEFAULT_WHEAT);
        grainType2.setBarley(DEFAULT_BARLEY);
    }

    @Test
    @Transactional
    public void createGrainType2() throws Exception {
        int databaseSizeBeforeCreate = grainType2Repository.findAll().size();

        // Create the GrainType2

        restGrainType2MockMvc.perform(post("/api/grainType2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType2)))
                .andExpect(status().isCreated());

        // Validate the GrainType2 in the database
        List<GrainType2> grainType2s = grainType2Repository.findAll();
        assertThat(grainType2s).hasSize(databaseSizeBeforeCreate + 1);
        GrainType2 testGrainType2 = grainType2s.get(grainType2s.size() - 1);
        assertThat(testGrainType2.getWheat()).isEqualTo(DEFAULT_WHEAT);
        assertThat(testGrainType2.getBarley()).isEqualTo(DEFAULT_BARLEY);
    }

    @Test
    @Transactional
    public void getAllGrainType2s() throws Exception {
        // Initialize the database
        grainType2Repository.saveAndFlush(grainType2);

        // Get all the grainType2s
        restGrainType2MockMvc.perform(get("/api/grainType2s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grainType2.getId().intValue())))
                .andExpect(jsonPath("$.[*].wheat").value(hasItem(DEFAULT_WHEAT.doubleValue())))
                .andExpect(jsonPath("$.[*].barley").value(hasItem(DEFAULT_BARLEY.doubleValue())));
    }

    @Test
    @Transactional
    public void getGrainType2() throws Exception {
        // Initialize the database
        grainType2Repository.saveAndFlush(grainType2);

        // Get the grainType2
        restGrainType2MockMvc.perform(get("/api/grainType2s/{id}", grainType2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(grainType2.getId().intValue()))
            .andExpect(jsonPath("$.wheat").value(DEFAULT_WHEAT.doubleValue()))
            .andExpect(jsonPath("$.barley").value(DEFAULT_BARLEY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrainType2() throws Exception {
        // Get the grainType2
        restGrainType2MockMvc.perform(get("/api/grainType2s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrainType2() throws Exception {
        // Initialize the database
        grainType2Repository.saveAndFlush(grainType2);

		int databaseSizeBeforeUpdate = grainType2Repository.findAll().size();

        // Update the grainType2
        grainType2.setWheat(UPDATED_WHEAT);
        grainType2.setBarley(UPDATED_BARLEY);
        

        restGrainType2MockMvc.perform(put("/api/grainType2s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType2)))
                .andExpect(status().isOk());

        // Validate the GrainType2 in the database
        List<GrainType2> grainType2s = grainType2Repository.findAll();
        assertThat(grainType2s).hasSize(databaseSizeBeforeUpdate);
        GrainType2 testGrainType2 = grainType2s.get(grainType2s.size() - 1);
        assertThat(testGrainType2.getWheat()).isEqualTo(UPDATED_WHEAT);
        assertThat(testGrainType2.getBarley()).isEqualTo(UPDATED_BARLEY);
    }

    @Test
    @Transactional
    public void deleteGrainType2() throws Exception {
        // Initialize the database
        grainType2Repository.saveAndFlush(grainType2);

		int databaseSizeBeforeDelete = grainType2Repository.findAll().size();

        // Get the grainType2
        restGrainType2MockMvc.perform(delete("/api/grainType2s/{id}", grainType2.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GrainType2> grainType2s = grainType2Repository.findAll();
        assertThat(grainType2s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
