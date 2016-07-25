package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.GrainType1;
import com.apa.namaa.repository.GrainType1Repository;

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
 * Test class for the GrainType1Resource REST controller.
 *
 * @see GrainType1Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GrainType1ResourceTest {


    private static final Double DEFAULT_SPELT = 1D;
    private static final Double UPDATED_SPELT = 2D;

    private static final Double DEFAULT_CORN = 1D;
    private static final Double UPDATED_CORN = 2D;

    private static final Double DEFAULT_MILLET = 1D;
    private static final Double UPDATED_MILLET = 2D;

    @Inject
    private GrainType1Repository grainType1Repository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restGrainType1MockMvc;

    private GrainType1 grainType1;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GrainType1Resource grainType1Resource = new GrainType1Resource();
        ReflectionTestUtils.setField(grainType1Resource, "grainType1Repository", grainType1Repository);
        this.restGrainType1MockMvc = MockMvcBuilders.standaloneSetup(grainType1Resource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        grainType1 = new GrainType1();
        grainType1.setSpelt(DEFAULT_SPELT);
        grainType1.setCorn(DEFAULT_CORN);
        grainType1.setMillet(DEFAULT_MILLET);
    }

    @Test
    @Transactional
    public void createGrainType1() throws Exception {
        int databaseSizeBeforeCreate = grainType1Repository.findAll().size();

        // Create the GrainType1

        restGrainType1MockMvc.perform(post("/api/grainType1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType1)))
                .andExpect(status().isCreated());

        // Validate the GrainType1 in the database
        List<GrainType1> grainType1s = grainType1Repository.findAll();
        assertThat(grainType1s).hasSize(databaseSizeBeforeCreate + 1);
        GrainType1 testGrainType1 = grainType1s.get(grainType1s.size() - 1);
        assertThat(testGrainType1.getSpelt()).isEqualTo(DEFAULT_SPELT);
        assertThat(testGrainType1.getCorn()).isEqualTo(DEFAULT_CORN);
        assertThat(testGrainType1.getMillet()).isEqualTo(DEFAULT_MILLET);
    }

    @Test
    @Transactional
    public void getAllGrainType1s() throws Exception {
        // Initialize the database
        grainType1Repository.saveAndFlush(grainType1);

        // Get all the grainType1s
        restGrainType1MockMvc.perform(get("/api/grainType1s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(grainType1.getId().intValue())))
                .andExpect(jsonPath("$.[*].spelt").value(hasItem(DEFAULT_SPELT.doubleValue())))
                .andExpect(jsonPath("$.[*].corn").value(hasItem(DEFAULT_CORN.doubleValue())))
                .andExpect(jsonPath("$.[*].millet").value(hasItem(DEFAULT_MILLET.doubleValue())));
    }

    @Test
    @Transactional
    public void getGrainType1() throws Exception {
        // Initialize the database
        grainType1Repository.saveAndFlush(grainType1);

        // Get the grainType1
        restGrainType1MockMvc.perform(get("/api/grainType1s/{id}", grainType1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(grainType1.getId().intValue()))
            .andExpect(jsonPath("$.spelt").value(DEFAULT_SPELT.doubleValue()))
            .andExpect(jsonPath("$.corn").value(DEFAULT_CORN.doubleValue()))
            .andExpect(jsonPath("$.millet").value(DEFAULT_MILLET.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGrainType1() throws Exception {
        // Get the grainType1
        restGrainType1MockMvc.perform(get("/api/grainType1s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrainType1() throws Exception {
        // Initialize the database
        grainType1Repository.saveAndFlush(grainType1);

		int databaseSizeBeforeUpdate = grainType1Repository.findAll().size();

        // Update the grainType1
        grainType1.setSpelt(UPDATED_SPELT);
        grainType1.setCorn(UPDATED_CORN);
        grainType1.setMillet(UPDATED_MILLET);
        

        restGrainType1MockMvc.perform(put("/api/grainType1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(grainType1)))
                .andExpect(status().isOk());

        // Validate the GrainType1 in the database
        List<GrainType1> grainType1s = grainType1Repository.findAll();
        assertThat(grainType1s).hasSize(databaseSizeBeforeUpdate);
        GrainType1 testGrainType1 = grainType1s.get(grainType1s.size() - 1);
        assertThat(testGrainType1.getSpelt()).isEqualTo(UPDATED_SPELT);
        assertThat(testGrainType1.getCorn()).isEqualTo(UPDATED_CORN);
        assertThat(testGrainType1.getMillet()).isEqualTo(UPDATED_MILLET);
    }

    @Test
    @Transactional
    public void deleteGrainType1() throws Exception {
        // Initialize the database
        grainType1Repository.saveAndFlush(grainType1);

		int databaseSizeBeforeDelete = grainType1Repository.findAll().size();

        // Get the grainType1
        restGrainType1MockMvc.perform(delete("/api/grainType1s/{id}", grainType1.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GrainType1> grainType1s = grainType1Repository.findAll();
        assertThat(grainType1s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
