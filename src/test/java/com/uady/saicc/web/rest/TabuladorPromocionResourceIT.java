package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.TabuladorPromocion;
import com.uady.saicc.repository.TabuladorPromocionRepository;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
import com.uady.saicc.service.mapper.TabuladorPromocionMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link TabuladorPromocionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TabuladorPromocionResourceIT {

    private static final LocalDate DEFAULT_INICIO_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INICIO_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FIN_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIN_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tabulador-promocions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TabuladorPromocionRepository tabuladorPromocionRepository;

    @Autowired
    private TabuladorPromocionMapper tabuladorPromocionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTabuladorPromocionMockMvc;

    private TabuladorPromocion tabuladorPromocion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabuladorPromocion createEntity(EntityManager em) {
        TabuladorPromocion tabuladorPromocion = new TabuladorPromocion()
            .inicioVigencia(DEFAULT_INICIO_VIGENCIA)
            .finVigencia(DEFAULT_FIN_VIGENCIA)
            .descripcion(DEFAULT_DESCRIPCION)
            .activo(DEFAULT_ACTIVO)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return tabuladorPromocion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabuladorPromocion createUpdatedEntity(EntityManager em) {
        TabuladorPromocion tabuladorPromocion = new TabuladorPromocion()
            .inicioVigencia(UPDATED_INICIO_VIGENCIA)
            .finVigencia(UPDATED_FIN_VIGENCIA)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return tabuladorPromocion;
    }

    @BeforeEach
    public void initTest() {
        tabuladorPromocion = createEntity(em);
    }

    @Test
    @Transactional
    void createTabuladorPromocion() throws Exception {
        int databaseSizeBeforeCreate = tabuladorPromocionRepository.findAll().size();
        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);
        restTabuladorPromocionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeCreate + 1);
        TabuladorPromocion testTabuladorPromocion = tabuladorPromocionList.get(tabuladorPromocionList.size() - 1);
        assertThat(testTabuladorPromocion.getInicioVigencia()).isEqualTo(DEFAULT_INICIO_VIGENCIA);
        assertThat(testTabuladorPromocion.getFinVigencia()).isEqualTo(DEFAULT_FIN_VIGENCIA);
        assertThat(testTabuladorPromocion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTabuladorPromocion.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testTabuladorPromocion.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTabuladorPromocion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTabuladorPromocion.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTabuladorPromocionWithExistingId() throws Exception {
        // Create the TabuladorPromocion with an existing ID
        tabuladorPromocion.setId(1L);
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        int databaseSizeBeforeCreate = tabuladorPromocionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabuladorPromocionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTabuladorPromocions() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        // Get all the tabuladorPromocionList
        restTabuladorPromocionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabuladorPromocion.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicioVigencia").value(hasItem(DEFAULT_INICIO_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].finVigencia").value(hasItem(DEFAULT_FIN_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTabuladorPromocion() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        // Get the tabuladorPromocion
        restTabuladorPromocionMockMvc
            .perform(get(ENTITY_API_URL_ID, tabuladorPromocion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tabuladorPromocion.getId().intValue()))
            .andExpect(jsonPath("$.inicioVigencia").value(DEFAULT_INICIO_VIGENCIA.toString()))
            .andExpect(jsonPath("$.finVigencia").value(DEFAULT_FIN_VIGENCIA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTabuladorPromocion() throws Exception {
        // Get the tabuladorPromocion
        restTabuladorPromocionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTabuladorPromocion() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();

        // Update the tabuladorPromocion
        TabuladorPromocion updatedTabuladorPromocion = tabuladorPromocionRepository.findById(tabuladorPromocion.getId()).get();
        // Disconnect from session so that the updates on updatedTabuladorPromocion are not directly saved in db
        em.detach(updatedTabuladorPromocion);
        updatedTabuladorPromocion
            .inicioVigencia(UPDATED_INICIO_VIGENCIA)
            .finVigencia(UPDATED_FIN_VIGENCIA)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(updatedTabuladorPromocion);

        restTabuladorPromocionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabuladorPromocionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
        TabuladorPromocion testTabuladorPromocion = tabuladorPromocionList.get(tabuladorPromocionList.size() - 1);
        assertThat(testTabuladorPromocion.getInicioVigencia()).isEqualTo(UPDATED_INICIO_VIGENCIA);
        assertThat(testTabuladorPromocion.getFinVigencia()).isEqualTo(UPDATED_FIN_VIGENCIA);
        assertThat(testTabuladorPromocion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTabuladorPromocion.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testTabuladorPromocion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTabuladorPromocion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorPromocion.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabuladorPromocionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTabuladorPromocionWithPatch() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();

        // Update the tabuladorPromocion using partial update
        TabuladorPromocion partialUpdatedTabuladorPromocion = new TabuladorPromocion();
        partialUpdatedTabuladorPromocion.setId(tabuladorPromocion.getId());

        partialUpdatedTabuladorPromocion.finVigencia(UPDATED_FIN_VIGENCIA).createdDate(UPDATED_CREATED_DATE);

        restTabuladorPromocionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabuladorPromocion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabuladorPromocion))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
        TabuladorPromocion testTabuladorPromocion = tabuladorPromocionList.get(tabuladorPromocionList.size() - 1);
        assertThat(testTabuladorPromocion.getInicioVigencia()).isEqualTo(DEFAULT_INICIO_VIGENCIA);
        assertThat(testTabuladorPromocion.getFinVigencia()).isEqualTo(UPDATED_FIN_VIGENCIA);
        assertThat(testTabuladorPromocion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTabuladorPromocion.getActivo()).isEqualTo(DEFAULT_ACTIVO);
        assertThat(testTabuladorPromocion.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTabuladorPromocion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorPromocion.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTabuladorPromocionWithPatch() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();

        // Update the tabuladorPromocion using partial update
        TabuladorPromocion partialUpdatedTabuladorPromocion = new TabuladorPromocion();
        partialUpdatedTabuladorPromocion.setId(tabuladorPromocion.getId());

        partialUpdatedTabuladorPromocion
            .inicioVigencia(UPDATED_INICIO_VIGENCIA)
            .finVigencia(UPDATED_FIN_VIGENCIA)
            .descripcion(UPDATED_DESCRIPCION)
            .activo(UPDATED_ACTIVO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTabuladorPromocionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabuladorPromocion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabuladorPromocion))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
        TabuladorPromocion testTabuladorPromocion = tabuladorPromocionList.get(tabuladorPromocionList.size() - 1);
        assertThat(testTabuladorPromocion.getInicioVigencia()).isEqualTo(UPDATED_INICIO_VIGENCIA);
        assertThat(testTabuladorPromocion.getFinVigencia()).isEqualTo(UPDATED_FIN_VIGENCIA);
        assertThat(testTabuladorPromocion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTabuladorPromocion.getActivo()).isEqualTo(UPDATED_ACTIVO);
        assertThat(testTabuladorPromocion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTabuladorPromocion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorPromocion.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tabuladorPromocionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTabuladorPromocion() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorPromocionRepository.findAll().size();
        tabuladorPromocion.setId(count.incrementAndGet());

        // Create the TabuladorPromocion
        TabuladorPromocionDTO tabuladorPromocionDTO = tabuladorPromocionMapper.toDto(tabuladorPromocion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorPromocionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorPromocionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabuladorPromocion in the database
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTabuladorPromocion() throws Exception {
        // Initialize the database
        tabuladorPromocionRepository.saveAndFlush(tabuladorPromocion);

        int databaseSizeBeforeDelete = tabuladorPromocionRepository.findAll().size();

        // Delete the tabuladorPromocion
        restTabuladorPromocionMockMvc
            .perform(delete(ENTITY_API_URL_ID, tabuladorPromocion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TabuladorPromocion> tabuladorPromocionList = tabuladorPromocionRepository.findAll();
        assertThat(tabuladorPromocionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
