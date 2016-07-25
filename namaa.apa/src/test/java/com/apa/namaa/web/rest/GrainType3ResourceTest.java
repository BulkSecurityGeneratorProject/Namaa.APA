package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.GrainType3;
import com.apa.namaa.repository.GrainType3Repository;

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
 * Test class for the GrainType3Resource REST controller.
 *
 * @see GrainType3Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GrainType3ResourceTest {


    private static final Double DEFAULT_CHICKPEAS = 1D;
    private static final Double UPDATED_CHICKPEAS = 2D;

    private static final Double DEFAULT_BEANS = 1D;
    private static final Double UPDATED_BEANS = 2D;

    private static final Double DEFAULT_COWPEA = 1D;
    private static final Double UPDATED_COWPEA = 2D;

    private static final Double DEFAULT_LATHYRUS = 1D;
    private static final Double UPDATED_LATHYRUS = 2D;

    @Inject
    private GrainType3Repository grainType3Repository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restGrainType3MockMvc;

    private GrainType3 grainType3;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrainType3Resource grainType3Resource = new GrainType3Resource();
        ReflectionTestUtils.setField(grainType3Resource, "grainType3Repository", grainType3Repository);
        this.restGrainType3MockMvc = MockMvcBuilders.standaloneSetup(grainType3Resource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grainType3 = new GrainType3();
        grainType3.setChickpeas(DEFAULT_CHICKPEAS);
        grainType3.setBeans(DEFAULT_BEANS);
        grainType3.setCowpea(DEFAULT_COWPEA);
        grainType3.setLathyrus(DEFAULT_LATHYRUS);
    }

    @Test
    @Transactional
    public void createGrainType3() throws Exception {
        int databaseSizeBeforeCreate = grainType3Repository.findAll().size();

        // Create the GrainType3

        restGrainType3MockMvc.perform(post("/api/grainType3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType3)))
                .andExpect(status().isCreated());

        // Validate the GrainType3 in the database
        List<GrainType3> grainType3s = grainType3Repository.findAll();
        assertThat(grainType3s).hasSize(databaseSizeBeforeCreate + 1);
        GrainType3 testGrainType3 = grainType3s.get(grainType3s.size() - 1);
        assertThat(testGrainType3.getChickpeas()).isEqualTo(DEFAULT_CHICKPEAS);
        assertThat(testGrainType3.getBeans()).isEqualTo(DEFAULT_BEANS);
        assertThat(testGrainType3.getCowpea()).isEqualTo(DEFAULT_COWPEA);
        assertThat(testGrainType3.getLathyrus()).isEqualTo(DEFAULT_LATHYRUS);
    }

    @Test
    @Transactional
    public void getAllGrainType3s() throws Exception {
        // Initialize the database
        grainType3Repository.saveAndFlush(grainType3);

        // Get all the grainType3s
        restGrainType3MockMvc.perform(get("/api/grainType3s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grainType3.getId().intValue())))
                .andExpect(jsonPath("$.[*].chickpeas").value(hasItem(DEFAULT_CHICKPEAS.doubleValue())))
                .andExpect(jsonPath("$.[*].beans").value(hasItem(DEFAULT_BEANS.doubleValue())))
                .andExpect(jsonPath("$.[*].cowpea").value(hasItem(DEFAULT_COWPEA.doubleValue())))
                .andExpect(jsonPath("$.[*].lathyrus").value(hasItem(DEFAULT_LATHYRUS.doubleValue())));
    }

    @Test
    @Transactional
    public void getGrainType3() throws Exception {
        // Initialize the database
        grainType3Repository.saveAndFlush(grainType3);

        // Get the grainType3
        restGrainType3MockMvc.perform(get("/api/grainType3s/{id}", grainType3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(grainType3.getId().intValue()))
            .andExpect(jsonPath("$.chickpeas").value(DEFAULT_CHICKPEAS.doubleValue()))
            .andExpect(jsonPath("$.beans").value(DEFAULT_BEANS.doubleValue()))
            .andExpect(jsonPath("$.cowpea").value(DEFAULT_COWPEA.doubleValue()))
            .andExpect(jsonPath("$.lathyrus").value(DEFAULT_LATHYRUS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrainType3() throws Exception {
        // Get the grainType3
        restGrainType3MockMvc.perform(get("/api/grainType3s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrainType3() throws Exception {
        // Initialize the database
        grainType3Repository.saveAndFlush(grainType3);

		int databaseSizeBeforeUpdate = grainType3Repository.findAll().size();

        // Update the grainType3
        grainType3.setChickpeas(UPDATED_CHICKPEAS);
        grainType3.setBeans(UPDATED_BEANS);
        grainType3.setCowpea(UPDATED_COWPEA);
        grainType3.setLathyrus(UPDATED_LATHYRUS);
        

        restGrainType3MockMvc.perform(put("/api/grainType3s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType3)))
                .andExpect(status().isOk());

        // Validate the GrainType3 in the database
        List<GrainType3> grainType3s = grainType3Repository.findAll();
        assertThat(grainType3s).hasSize(databaseSizeBeforeUpdate);
        GrainType3 testGrainType3 = grainType3s.get(grainType3s.size() - 1);
        assertThat(testGrainType3.getChickpeas()).isEqualTo(UPDATED_CHICKPEAS);
        assertThat(testGrainType3.getBeans()).isEqualTo(UPDATED_BEANS);
        assertThat(testGrainType3.getCowpea()).isEqualTo(UPDATED_COWPEA);
        assertThat(testGrainType3.getLathyrus()).isEqualTo(UPDATED_LATHYRUS);
    }

    @Test
    @Transactional
    public void deleteGrainType3() throws Exception {
        // Initialize the database
        grainType3Repository.saveAndFlush(grainType3);

		int databaseSizeBeforeDelete = grainType3Repository.findAll().size();

        // Get the grainType3
        restGrainType3MockMvc.perform(delete("/api/grainType3s/{id}", grainType3.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GrainType3> grainType3s = grainType3Repository.findAll();
        assertThat(grainType3s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
