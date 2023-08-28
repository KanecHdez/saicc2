package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.ComisionDictaminadora;
import com.uady.saicc.repository.ComisionDictaminadoraRepository;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
import com.uady.saicc.service.mapper.ComisionDictaminadoraMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComisionDictaminadoraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComisionDictaminadoraResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/comision-dictaminadoras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComisionDictaminadoraRepository comisionDictaminadoraRepository;

    @Autowired
    private ComisionDictaminadoraMapper comisionDictaminadoraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComisionDictaminadoraMockMvc;

    private ComisionDictaminadora comisionDictaminadora;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComisionDictaminadora createEntity(EntityManager em) {
        ComisionDictaminadora comisionDictaminadora = new ComisionDictaminadora().nombre(DEFAULT_NOMBRE);
        return comisionDictaminadora;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComisionDictaminadora createUpdatedEntity(EntityManager em) {
        ComisionDictaminadora comisionDictaminadora = new ComisionDictaminadora().nombre(UPDATED_NOMBRE);
        return comisionDictaminadora;
    }

    @BeforeEach
    public void initTest() {
        comisionDictaminadora = createEntity(em);
    }

    @Test
    @Transactional
    void createComisionDictaminadora() throws Exception {
        int databaseSizeBeforeCreate = comisionDictaminadoraRepository.findAll().size();
        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);
        restComisionDictaminadoraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeCreate + 1);
        ComisionDictaminadora testComisionDictaminadora = comisionDictaminadoraList.get(comisionDictaminadoraList.size() - 1);
        assertThat(testComisionDictaminadora.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createComisionDictaminadoraWithExistingId() throws Exception {
        // Create the ComisionDictaminadora with an existing ID
        comisionDictaminadora.setId(1L);
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        int databaseSizeBeforeCreate = comisionDictaminadoraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComisionDictaminadoraMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComisionDictaminadoras() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        // Get all the comisionDictaminadoraList
        restComisionDictaminadoraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comisionDictaminadora.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getComisionDictaminadora() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        // Get the comisionDictaminadora
        restComisionDictaminadoraMockMvc
            .perform(get(ENTITY_API_URL_ID, comisionDictaminadora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comisionDictaminadora.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingComisionDictaminadora() throws Exception {
        // Get the comisionDictaminadora
        restComisionDictaminadoraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComisionDictaminadora() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();

        // Update the comisionDictaminadora
        ComisionDictaminadora updatedComisionDictaminadora = comisionDictaminadoraRepository.findById(comisionDictaminadora.getId()).get();
        // Disconnect from session so that the updates on updatedComisionDictaminadora are not directly saved in db
        em.detach(updatedComisionDictaminadora);
        updatedComisionDictaminadora.nombre(UPDATED_NOMBRE);
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(updatedComisionDictaminadora);

        restComisionDictaminadoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comisionDictaminadoraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
        ComisionDictaminadora testComisionDictaminadora = comisionDictaminadoraList.get(comisionDictaminadoraList.size() - 1);
        assertThat(testComisionDictaminadora.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comisionDictaminadoraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComisionDictaminadoraWithPatch() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();

        // Update the comisionDictaminadora using partial update
        ComisionDictaminadora partialUpdatedComisionDictaminadora = new ComisionDictaminadora();
        partialUpdatedComisionDictaminadora.setId(comisionDictaminadora.getId());

        partialUpdatedComisionDictaminadora.nombre(UPDATED_NOMBRE);

        restComisionDictaminadoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComisionDictaminadora.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComisionDictaminadora))
            )
            .andExpect(status().isOk());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
        ComisionDictaminadora testComisionDictaminadora = comisionDictaminadoraList.get(comisionDictaminadoraList.size() - 1);
        assertThat(testComisionDictaminadora.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateComisionDictaminadoraWithPatch() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();

        // Update the comisionDictaminadora using partial update
        ComisionDictaminadora partialUpdatedComisionDictaminadora = new ComisionDictaminadora();
        partialUpdatedComisionDictaminadora.setId(comisionDictaminadora.getId());

        partialUpdatedComisionDictaminadora.nombre(UPDATED_NOMBRE);

        restComisionDictaminadoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComisionDictaminadora.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComisionDictaminadora))
            )
            .andExpect(status().isOk());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
        ComisionDictaminadora testComisionDictaminadora = comisionDictaminadoraList.get(comisionDictaminadoraList.size() - 1);
        assertThat(testComisionDictaminadora.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comisionDictaminadoraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComisionDictaminadora() throws Exception {
        int databaseSizeBeforeUpdate = comisionDictaminadoraRepository.findAll().size();
        comisionDictaminadora.setId(count.incrementAndGet());

        // Create the ComisionDictaminadora
        ComisionDictaminadoraDTO comisionDictaminadoraDTO = comisionDictaminadoraMapper.toDto(comisionDictaminadora);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComisionDictaminadoraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comisionDictaminadoraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComisionDictaminadora in the database
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComisionDictaminadora() throws Exception {
        // Initialize the database
        comisionDictaminadoraRepository.saveAndFlush(comisionDictaminadora);

        int databaseSizeBeforeDelete = comisionDictaminadoraRepository.findAll().size();

        // Delete the comisionDictaminadora
        restComisionDictaminadoraMockMvc
            .perform(delete(ENTITY_API_URL_ID, comisionDictaminadora.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComisionDictaminadora> comisionDictaminadoraList = comisionDictaminadoraRepository.findAll();
        assertThat(comisionDictaminadoraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
