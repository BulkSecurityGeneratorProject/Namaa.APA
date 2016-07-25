package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.Olive;
import com.apa.namaa.repository.OliveRepository;

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
 * Test class for the OliveResource REST controller.
 *
 * @see OliveResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OliveResourceTest {


    private static final Double DEFAULT_OLIVE_OIL = 1D;
    private static final Double UPDATED_OLIVE_OIL = 2D;

    @Inject
    private OliveRepository oliveRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restOliveMockMvc;

    private Olive olive;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OliveResource oliveResource = new OliveResource();
        ReflectionTestUtils.setField(oliveResource, "oliveRepository", oliveRepository);
        this.restOliveMockMvc = MockMvcBuilders.standaloneSetup(oliveResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        olive = new Olive();
        olive.setOliveOil(DEFAULT_OLIVE_OIL);
    }

    @Test
    @Transactional
    public void createOlive() throws Exception {
        int databaseSizeBeforeCreate = oliveRepository.findAll().size();

        // Create the Olive

        restOliveMockMvc.perform(post("/api/olives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(olive)))
                .andExpect(status().isCreated());

        // Validate the Olive in the database
        List<Olive> olives = oliveRepository.findAll();
        assertThat(olives).hasSize(databaseSizeBeforeCreate + 1);
        Olive testOlive = olives.get(olives.size() - 1);
        assertThat(testOlive.getOliveOil()).isEqualTo(DEFAULT_OLIVE_OIL);
    }

    @Test
    @Transactional
    public void getAllOlives() throws Exception {
        // Initialize the database
        oliveRepository.saveAndFlush(olive);

        // Get all the olives
        restOliveMockMvc.perform(get("/api/olives"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(olive.getId().intValue())))
                .andExpect(jsonPath("$.[*].oliveOil").value(hasItem(DEFAULT_OLIVE_OIL.doubleValue())));
    }

    @Test
    @Transactional
    public void getOlive() throws Exception {
        // Initialize the database
        oliveRepository.saveAndFlush(olive);

        // Get the olive
        restOliveMockMvc.perform(get("/api/olives/{id}", olive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(olive.getId().intValue()))
            .andExpect(jsonPath("$.oliveOil").value(DEFAULT_OLIVE_OIL.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOlive() throws Exception {
        // Get the olive
        restOliveMockMvc.perform(get("/api/olives/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOlive() throws Exception {
        // Initialize the database
        oliveRepository.saveAndFlush(olive);

		int databaseSizeBeforeUpdate = oliveRepository.findAll().size();

        // Update the olive
        olive.setOliveOil(UPDATED_OLIVE_OIL);
        

        restOliveMockMvc.perform(put("/api/olives")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(olive)))
                .andExpect(status().isOk());

        // Validate the Olive in the database
        List<Olive> olives = oliveRepository.findAll();
        assertThat(olives).hasSize(databaseSizeBeforeUpdate);
        Olive testOlive = olives.get(olives.size() - 1);
        assertThat(testOlive.getOliveOil()).isEqualTo(UPDATED_OLIVE_OIL);
    }

    @Test
    @Transactional
    public void deleteOlive() throws Exception {
        // Initialize the database
        oliveRepository.saveAndFlush(olive);

		int databaseSizeBeforeDelete = oliveRepository.findAll().size();

        // Get the olive
        restOliveMockMvc.perform(delete("/api/olives/{id}", olive.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Olive> olives = oliveRepository.findAll();
        assertThat(olives).hasSize(databaseSizeBeforeDelete - 1);
    }
}
