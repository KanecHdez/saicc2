package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.Periodo;
import com.uady.saicc.repository.PeriodoRepository;
import com.uady.saicc.service.dto.PeriodoDTO;
import com.uady.saicc.service.mapper.PeriodoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link PeriodoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeriodoResourceIT {

    private static final Integer DEFAULT_ANIO = 1;
    private static final Integer UPDATED_ANIO = 2;

    private static final Integer DEFAULT_PERIODO = 1;
    private static final Integer UPDATED_PERIODO = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/periodos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private PeriodoMapper periodoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodoMockMvc;

    private Periodo periodo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodo createEntity(EntityManager em) {
        Periodo periodo = new Periodo()
            .anio(DEFAULT_ANIO)
            .periodo(DEFAULT_PERIODO)
            .descripcion(DEFAULT_DESCRIPCION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return periodo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodo createUpdatedEntity(EntityManager em) {
        Periodo periodo = new Periodo()
            .anio(UPDATED_ANIO)
            .periodo(UPDATED_PERIODO)
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return periodo;
    }

    @BeforeEach
    public void initTest() {
        periodo = createEntity(em);
    }

    @Test
    @Transactional
    void createPeriodo() throws Exception {
        int databaseSizeBeforeCreate = periodoRepository.findAll().size();
        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);
        restPeriodoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate + 1);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testPeriodo.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testPeriodo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPeriodo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPeriodo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPeriodo.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPeriodoWithExistingId() throws Exception {
        // Create the Periodo with an existing ID
        periodo.setId(1L);
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        int databaseSizeBeforeCreate = periodoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeriodos() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get all the periodoList
        restPeriodoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodo.getId().intValue())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        // Get the periodo
        restPeriodoMockMvc
            .perform(get(ENTITY_API_URL_ID, periodo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodo.getId().intValue()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPeriodo() throws Exception {
        // Get the periodo
        restPeriodoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Update the periodo
        Periodo updatedPeriodo = periodoRepository.findById(periodo.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodo are not directly saved in db
        em.detach(updatedPeriodo);
        updatedPeriodo
            .anio(UPDATED_ANIO)
            .periodo(UPDATED_PERIODO)
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PeriodoDTO periodoDTO = periodoMapper.toDto(updatedPeriodo);

        restPeriodoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testPeriodo.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testPeriodo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPeriodo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPeriodo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPeriodo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(periodoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodoWithPatch() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Update the periodo using partial update
        Periodo partialUpdatedPeriodo = new Periodo();
        partialUpdatedPeriodo.setId(periodo.getId());

        partialUpdatedPeriodo.descripcion(UPDATED_DESCRIPCION).modifiedBy(UPDATED_MODIFIED_BY).createdDate(UPDATED_CREATED_DATE);

        restPeriodoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodo))
            )
            .andExpect(status().isOk());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testPeriodo.getPeriodo()).isEqualTo(DEFAULT_PERIODO);
        assertThat(testPeriodo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPeriodo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPeriodo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPeriodo.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePeriodoWithPatch() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();

        // Update the periodo using partial update
        Periodo partialUpdatedPeriodo = new Periodo();
        partialUpdatedPeriodo.setId(periodo.getId());

        partialUpdatedPeriodo
            .anio(UPDATED_ANIO)
            .periodo(UPDATED_PERIODO)
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPeriodoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPeriodo))
            )
            .andExpect(status().isOk());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
        Periodo testPeriodo = periodoList.get(periodoList.size() - 1);
        assertThat(testPeriodo.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testPeriodo.getPeriodo()).isEqualTo(UPDATED_PERIODO);
        assertThat(testPeriodo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPeriodo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPeriodo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPeriodo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodo() throws Exception {
        int databaseSizeBeforeUpdate = periodoRepository.findAll().size();
        periodo.setId(count.incrementAndGet());

        // Create the Periodo
        PeriodoDTO periodoDTO = periodoMapper.toDto(periodo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(periodoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Periodo in the database
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodo() throws Exception {
        // Initialize the database
        periodoRepository.saveAndFlush(periodo);

        int databaseSizeBeforeDelete = periodoRepository.findAll().size();

        // Delete the periodo
        restPeriodoMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Periodo> periodoList = periodoRepository.findAll();
        assertThat(periodoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
