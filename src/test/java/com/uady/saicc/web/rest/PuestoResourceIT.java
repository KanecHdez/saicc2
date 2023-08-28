package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.Puesto;
import com.uady.saicc.repository.PuestoRepository;
import com.uady.saicc.service.dto.PuestoDTO;
import com.uady.saicc.service.mapper.PuestoMapper;
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
 * Integration tests for the {@link PuestoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PuestoResourceIT {

    private static final Integer DEFAULT_CVE = 1;
    private static final Integer UPDATED_CVE = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Float DEFAULT_PUNTAJE = 1F;
    private static final Float UPDATED_PUNTAJE = 2F;

    private static final Integer DEFAULT_RANKING = 1;
    private static final Integer UPDATED_RANKING = 2;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/puestos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private PuestoMapper puestoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPuestoMockMvc;

    private Puesto puesto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puesto createEntity(EntityManager em) {
        Puesto puesto = new Puesto()
            .cve(DEFAULT_CVE)
            .nombre(DEFAULT_NOMBRE)
            .puntaje(DEFAULT_PUNTAJE)
            .ranking(DEFAULT_RANKING)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return puesto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Puesto createUpdatedEntity(EntityManager em) {
        Puesto puesto = new Puesto()
            .cve(UPDATED_CVE)
            .nombre(UPDATED_NOMBRE)
            .puntaje(UPDATED_PUNTAJE)
            .ranking(UPDATED_RANKING)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return puesto;
    }

    @BeforeEach
    public void initTest() {
        puesto = createEntity(em);
    }

    @Test
    @Transactional
    void createPuesto() throws Exception {
        int databaseSizeBeforeCreate = puestoRepository.findAll().size();
        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);
        restPuestoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isCreated());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeCreate + 1);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getCve()).isEqualTo(DEFAULT_CVE);
        assertThat(testPuesto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPuesto.getPuntaje()).isEqualTo(DEFAULT_PUNTAJE);
        assertThat(testPuesto.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testPuesto.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPuesto.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPuesto.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPuestoWithExistingId() throws Exception {
        // Create the Puesto with an existing ID
        puesto.setId(1L);
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        int databaseSizeBeforeCreate = puestoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPuestoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPuestos() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        // Get all the puestoList
        restPuestoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(puesto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cve").value(hasItem(DEFAULT_CVE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].puntaje").value(hasItem(DEFAULT_PUNTAJE.doubleValue())))
            .andExpect(jsonPath("$.[*].ranking").value(hasItem(DEFAULT_RANKING)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        // Get the puesto
        restPuestoMockMvc
            .perform(get(ENTITY_API_URL_ID, puesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(puesto.getId().intValue()))
            .andExpect(jsonPath("$.cve").value(DEFAULT_CVE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.puntaje").value(DEFAULT_PUNTAJE.doubleValue()))
            .andExpect(jsonPath("$.ranking").value(DEFAULT_RANKING))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPuesto() throws Exception {
        // Get the puesto
        restPuestoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();

        // Update the puesto
        Puesto updatedPuesto = puestoRepository.findById(puesto.getId()).get();
        // Disconnect from session so that the updates on updatedPuesto are not directly saved in db
        em.detach(updatedPuesto);
        updatedPuesto
            .cve(UPDATED_CVE)
            .nombre(UPDATED_NOMBRE)
            .puntaje(UPDATED_PUNTAJE)
            .ranking(UPDATED_RANKING)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PuestoDTO puestoDTO = puestoMapper.toDto(updatedPuesto);

        restPuestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, puestoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testPuesto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuesto.getPuntaje()).isEqualTo(UPDATED_PUNTAJE);
        assertThat(testPuesto.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testPuesto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPuesto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPuesto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, puestoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(puestoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePuestoWithPatch() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();

        // Update the puesto using partial update
        Puesto partialUpdatedPuesto = new Puesto();
        partialUpdatedPuesto.setId(puesto.getId());

        partialUpdatedPuesto.cve(UPDATED_CVE).nombre(UPDATED_NOMBRE).modifiedBy(UPDATED_MODIFIED_BY).createdDate(UPDATED_CREATED_DATE);

        restPuestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPuesto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPuesto))
            )
            .andExpect(status().isOk());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testPuesto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuesto.getPuntaje()).isEqualTo(DEFAULT_PUNTAJE);
        assertThat(testPuesto.getRanking()).isEqualTo(DEFAULT_RANKING);
        assertThat(testPuesto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPuesto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPuesto.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePuestoWithPatch() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();

        // Update the puesto using partial update
        Puesto partialUpdatedPuesto = new Puesto();
        partialUpdatedPuesto.setId(puesto.getId());

        partialUpdatedPuesto
            .cve(UPDATED_CVE)
            .nombre(UPDATED_NOMBRE)
            .puntaje(UPDATED_PUNTAJE)
            .ranking(UPDATED_RANKING)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPuestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPuesto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPuesto))
            )
            .andExpect(status().isOk());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
        Puesto testPuesto = puestoList.get(puestoList.size() - 1);
        assertThat(testPuesto.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testPuesto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPuesto.getPuntaje()).isEqualTo(UPDATED_PUNTAJE);
        assertThat(testPuesto.getRanking()).isEqualTo(UPDATED_RANKING);
        assertThat(testPuesto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPuesto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPuesto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, puestoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPuesto() throws Exception {
        int databaseSizeBeforeUpdate = puestoRepository.findAll().size();
        puesto.setId(count.incrementAndGet());

        // Create the Puesto
        PuestoDTO puestoDTO = puestoMapper.toDto(puesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPuestoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(puestoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Puesto in the database
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePuesto() throws Exception {
        // Initialize the database
        puestoRepository.saveAndFlush(puesto);

        int databaseSizeBeforeDelete = puestoRepository.findAll().size();

        // Delete the puesto
        restPuestoMockMvc
            .perform(delete(ENTITY_API_URL_ID, puesto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Puesto> puestoList = puestoRepository.findAll();
        assertThat(puestoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
