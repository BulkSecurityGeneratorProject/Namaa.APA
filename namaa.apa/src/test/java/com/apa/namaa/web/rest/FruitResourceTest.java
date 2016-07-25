package com.apa.namaa.web.rest;

import com.apa.namaa.Application;
import com.apa.namaa.domain.Fruit;
import com.apa.namaa.repository.FruitRepository;

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
 * Test class for the FruitResource REST controller.
 *
 * @see FruitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class FruitResourceTest {


    private static final Double DEFAULT_GRAPES = 1D;
    private static final Double UPDATED_GRAPES = 2D;

    private static final Double DEFAULT_DATES = 1D;
    private static final Double UPDATED_DATES = 2D;

    @Inject
    private FruitRepository fruitRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restFruitMockMvc;

    private Fruit fruit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FruitResource fruitResource = new FruitResource();
        ReflectionTestUtils.setField(fruitResource, "fruitRepository", fruitRepository);
        this.restFruitMockMvc = MockMvcBuilders.standaloneSetup(fruitResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        fruit = new Fruit();
        fruit.setGrapes(DEFAULT_GRAPES);
        fruit.setDates(DEFAULT_DATES);
    }

    @Test
    @Transactional
    public void createFruit() throws Exception {
        int databaseSizeBeforeCreate = fruitRepository.findAll().size();

        // Create the Fruit

        restFruitMockMvc.perform(post("/api/fruits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruit)))
                .andExpect(status().isCreated());

        // Validate the Fruit in the database
        List<Fruit> fruits = fruitRepository.findAll();
        assertThat(fruits).hasSize(databaseSizeBeforeCreate + 1);
        Fruit testFruit = fruits.get(fruits.size() - 1);
        assertThat(testFruit.getGrapes()).isEqualTo(DEFAULT_GRAPES);
        assertThat(testFruit.getDates()).isEqualTo(DEFAULT_DATES);
    }

    @Test
    @Transactional
    public void getAllFruits() throws Exception {
        // Initialize the database
        fruitRepository.saveAndFlush(fruit);

        // Get all the fruits
        restFruitMockMvc.perform(get("/api/fruits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fruit.getId().intValue())))
                .andExpect(jsonPath("$.[*].grapes").value(hasItem(DEFAULT_GRAPES.doubleValue())))
                .andExpect(jsonPath("$.[*].dates").value(hasItem(DEFAULT_DATES.doubleValue())));
    }

    @Test
    @Transactional
    public void getFruit() throws Exception {
        // Initialize the database
        fruitRepository.saveAndFlush(fruit);

        // Get the fruit
        restFruitMockMvc.perform(get("/api/fruits/{id}", fruit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(fruit.getId().intValue()))
            .andExpect(jsonPath("$.grapes").value(DEFAULT_GRAPES.doubleValue()))
            .andExpect(jsonPath("$.dates").value(DEFAULT_DATES.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFruit() throws Exception {
        // Get the fruit
        restFruitMockMvc.perform(get("/api/fruits/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFruit() throws Exception {
        // Initialize the database
        fruitRepository.saveAndFlush(fruit);

		int databaseSizeBeforeUpdate = fruitRepository.findAll().size();

        // Update the fruit
        fruit.setGrapes(UPDATED_GRAPES);
        fruit.setDates(UPDATED_DATES);
        

        restFruitMockMvc.perform(put("/api/fruits")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fruit)))
                .andExpect(status().isOk());

        // Validate the Fruit in the database
        List<Fruit> fruits = fruitRepository.findAll();
        assertThat(fruits).hasSize(databaseSizeBeforeUpdate);
        Fruit testFruit = fruits.get(fruits.size() - 1);
        assertThat(testFruit.getGrapes()).isEqualTo(UPDATED_GRAPES);
        assertThat(testFruit.getDates()).isEqualTo(UPDATED_DATES);
    }

    @Test
    @Transactional
    public void deleteFruit() throws Exception {
        // Initialize the database
        fruitRepository.saveAndFlush(fruit);

		int databaseSizeBeforeDelete = fruitRepository.findAll().size();

        // Get the fruit
        restFruitMockMvc.perform(delete("/api/fruits/{id}", fruit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fruit> fruits = fruitRepository.findAll();
        assertThat(fruits).hasSize(databaseSizeBeforeDelete - 1);
    }
}
