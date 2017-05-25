package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PruebaScrollApp;

import com.mycompany.myapp.domain.Scroll;
import com.mycompany.myapp.repository.ScrollRepository;
import com.mycompany.myapp.service.ScrollService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScrollResource REST controller.
 *
 * @see ScrollResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PruebaScrollApp.class)
public class ScrollResourceIntTest {

    private static final String DEFAULT_PRUEBA = "AAAAAAAAAA";
    private static final String UPDATED_PRUEBA = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRUEBA_2 = 1;
    private static final Integer UPDATED_PRUEBA_2 = 2;

    private static final String DEFAULT_PRUEBA_3 = "AAAAAAAAAA";
    private static final String UPDATED_PRUEBA_3 = "BBBBBBBBBB";

    @Autowired
    private ScrollRepository scrollRepository;

    @Autowired
    private ScrollService scrollService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restScrollMockMvc;

    private Scroll scroll;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScrollResource scrollResource = new ScrollResource(scrollService);
        this.restScrollMockMvc = MockMvcBuilders.standaloneSetup(scrollResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scroll createEntity(EntityManager em) {
        Scroll scroll = new Scroll()
                .prueba(DEFAULT_PRUEBA)
                .prueba2(DEFAULT_PRUEBA_2)
                .prueba3(DEFAULT_PRUEBA_3);
        return scroll;
    }

    @Before
    public void initTest() {
        scroll = createEntity(em);
    }

    @Test
    @Transactional
    public void createScroll() throws Exception {
        int databaseSizeBeforeCreate = scrollRepository.findAll().size();

        // Create the Scroll

        restScrollMockMvc.perform(post("/api/scrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scroll)))
            .andExpect(status().isCreated());

        // Validate the Scroll in the database
        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeCreate + 1);
        Scroll testScroll = scrollList.get(scrollList.size() - 1);
        assertThat(testScroll.getPrueba()).isEqualTo(DEFAULT_PRUEBA);
        assertThat(testScroll.getPrueba2()).isEqualTo(DEFAULT_PRUEBA_2);
        assertThat(testScroll.getPrueba3()).isEqualTo(DEFAULT_PRUEBA_3);
    }

    @Test
    @Transactional
    public void createScrollWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scrollRepository.findAll().size();

        // Create the Scroll with an existing ID
        Scroll existingScroll = new Scroll();
        existingScroll.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScrollMockMvc.perform(post("/api/scrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingScroll)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPruebaIsRequired() throws Exception {
        int databaseSizeBeforeTest = scrollRepository.findAll().size();
        // set the field null
        scroll.setPrueba(null);

        // Create the Scroll, which fails.

        restScrollMockMvc.perform(post("/api/scrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scroll)))
            .andExpect(status().isBadRequest());

        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScrolls() throws Exception {
        // Initialize the database
        scrollRepository.saveAndFlush(scroll);

        // Get all the scrollList
        restScrollMockMvc.perform(get("/api/scrolls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scroll.getId().intValue())))
            .andExpect(jsonPath("$.[*].prueba").value(hasItem(DEFAULT_PRUEBA.toString())))
            .andExpect(jsonPath("$.[*].prueba2").value(hasItem(DEFAULT_PRUEBA_2)))
            .andExpect(jsonPath("$.[*].prueba3").value(hasItem(DEFAULT_PRUEBA_3.toString())));
    }

    @Test
    @Transactional
    public void getScroll() throws Exception {
        // Initialize the database
        scrollRepository.saveAndFlush(scroll);

        // Get the scroll
        restScrollMockMvc.perform(get("/api/scrolls/{id}", scroll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scroll.getId().intValue()))
            .andExpect(jsonPath("$.prueba").value(DEFAULT_PRUEBA.toString()))
            .andExpect(jsonPath("$.prueba2").value(DEFAULT_PRUEBA_2))
            .andExpect(jsonPath("$.prueba3").value(DEFAULT_PRUEBA_3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScroll() throws Exception {
        // Get the scroll
        restScrollMockMvc.perform(get("/api/scrolls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScroll() throws Exception {
        // Initialize the database
        scrollService.save(scroll);

        int databaseSizeBeforeUpdate = scrollRepository.findAll().size();

        // Update the scroll
        Scroll updatedScroll = scrollRepository.findOne(scroll.getId());
        updatedScroll
                .prueba(UPDATED_PRUEBA)
                .prueba2(UPDATED_PRUEBA_2)
                .prueba3(UPDATED_PRUEBA_3);

        restScrollMockMvc.perform(put("/api/scrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScroll)))
            .andExpect(status().isOk());

        // Validate the Scroll in the database
        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeUpdate);
        Scroll testScroll = scrollList.get(scrollList.size() - 1);
        assertThat(testScroll.getPrueba()).isEqualTo(UPDATED_PRUEBA);
        assertThat(testScroll.getPrueba2()).isEqualTo(UPDATED_PRUEBA_2);
        assertThat(testScroll.getPrueba3()).isEqualTo(UPDATED_PRUEBA_3);
    }

    @Test
    @Transactional
    public void updateNonExistingScroll() throws Exception {
        int databaseSizeBeforeUpdate = scrollRepository.findAll().size();

        // Create the Scroll

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restScrollMockMvc.perform(put("/api/scrolls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scroll)))
            .andExpect(status().isCreated());

        // Validate the Scroll in the database
        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteScroll() throws Exception {
        // Initialize the database
        scrollService.save(scroll);

        int databaseSizeBeforeDelete = scrollRepository.findAll().size();

        // Get the scroll
        restScrollMockMvc.perform(delete("/api/scrolls/{id}", scroll.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Scroll> scrollList = scrollRepository.findAll();
        assertThat(scrollList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scroll.class);
    }
}
