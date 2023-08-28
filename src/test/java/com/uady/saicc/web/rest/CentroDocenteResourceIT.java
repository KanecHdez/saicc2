package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.CentroDocente;
import com.uady.saicc.repository.CentroDocenteRepository;
import com.uady.saicc.service.CentroDocenteService;
import com.uady.saicc.service.dto.CentroDocenteDTO;
import com.uady.saicc.service.mapper.CentroDocenteMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CentroDocenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CentroDocenteResourceIT {

    private static final Integer DEFAULT_CVE = 1;
    private static final Integer UPDATED_CVE = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-docentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CentroDocenteRepository centroDocenteRepository;

    @Mock
    private CentroDocenteRepository centroDocenteRepositoryMock;

    @Autowired
    private CentroDocenteMapper centroDocenteMapper;

    @Mock
    private CentroDocenteService centroDocenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroDocenteMockMvc;

    private CentroDocente centroDocente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroDocente createEntity(EntityManager em) {
        CentroDocente centroDocente = new CentroDocente().cve(DEFAULT_CVE).nombre(DEFAULT_NOMBRE);
        return centroDocente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroDocente createUpdatedEntity(EntityManager em) {
        CentroDocente centroDocente = new CentroDocente().cve(UPDATED_CVE).nombre(UPDATED_NOMBRE);
        return centroDocente;
    }

    @BeforeEach
    public void initTest() {
        centroDocente = createEntity(em);
    }

    @Test
    @Transactional
    void createCentroDocente() throws Exception {
        int databaseSizeBeforeCreate = centroDocenteRepository.findAll().size();
        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);
        restCentroDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeCreate + 1);
        CentroDocente testCentroDocente = centroDocenteList.get(centroDocenteList.size() - 1);
        assertThat(testCentroDocente.getCve()).isEqualTo(DEFAULT_CVE);
        assertThat(testCentroDocente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    void createCentroDocenteWithExistingId() throws Exception {
        // Create the CentroDocente with an existing ID
        centroDocente.setId(1L);
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        int databaseSizeBeforeCreate = centroDocenteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroDocenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCentroDocentes() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        // Get all the centroDocenteList
        restCentroDocenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroDocente.getId().intValue())))
            .andExpect(jsonPath("$.[*].cve").value(hasItem(DEFAULT_CVE)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCentroDocentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(centroDocenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCentroDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(centroDocenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCentroDocentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(centroDocenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCentroDocenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(centroDocenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCentroDocente() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        // Get the centroDocente
        restCentroDocenteMockMvc
            .perform(get(ENTITY_API_URL_ID, centroDocente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroDocente.getId().intValue()))
            .andExpect(jsonPath("$.cve").value(DEFAULT_CVE))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingCentroDocente() throws Exception {
        // Get the centroDocente
        restCentroDocenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroDocente() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();

        // Update the centroDocente
        CentroDocente updatedCentroDocente = centroDocenteRepository.findById(centroDocente.getId()).get();
        // Disconnect from session so that the updates on updatedCentroDocente are not directly saved in db
        em.detach(updatedCentroDocente);
        updatedCentroDocente.cve(UPDATED_CVE).nombre(UPDATED_NOMBRE);
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(updatedCentroDocente);

        restCentroDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroDocenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isOk());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
        CentroDocente testCentroDocente = centroDocenteList.get(centroDocenteList.size() - 1);
        assertThat(testCentroDocente.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testCentroDocente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void putNonExistingCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroDocenteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroDocenteWithPatch() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();

        // Update the centroDocente using partial update
        CentroDocente partialUpdatedCentroDocente = new CentroDocente();
        partialUpdatedCentroDocente.setId(centroDocente.getId());

        partialUpdatedCentroDocente.cve(UPDATED_CVE).nombre(UPDATED_NOMBRE);

        restCentroDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentroDocente))
            )
            .andExpect(status().isOk());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
        CentroDocente testCentroDocente = centroDocenteList.get(centroDocenteList.size() - 1);
        assertThat(testCentroDocente.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testCentroDocente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void fullUpdateCentroDocenteWithPatch() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();

        // Update the centroDocente using partial update
        CentroDocente partialUpdatedCentroDocente = new CentroDocente();
        partialUpdatedCentroDocente.setId(centroDocente.getId());

        partialUpdatedCentroDocente.cve(UPDATED_CVE).nombre(UPDATED_NOMBRE);

        restCentroDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroDocente.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCentroDocente))
            )
            .andExpect(status().isOk());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
        CentroDocente testCentroDocente = centroDocenteList.get(centroDocenteList.size() - 1);
        assertThat(testCentroDocente.getCve()).isEqualTo(UPDATED_CVE);
        assertThat(testCentroDocente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    void patchNonExistingCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroDocenteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroDocente() throws Exception {
        int databaseSizeBeforeUpdate = centroDocenteRepository.findAll().size();
        centroDocente.setId(count.incrementAndGet());

        // Create the CentroDocente
        CentroDocenteDTO centroDocenteDTO = centroDocenteMapper.toDto(centroDocente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroDocenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(centroDocenteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroDocente in the database
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroDocente() throws Exception {
        // Initialize the database
        centroDocenteRepository.saveAndFlush(centroDocente);

        int databaseSizeBeforeDelete = centroDocenteRepository.findAll().size();

        // Delete the centroDocente
        restCentroDocenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroDocente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CentroDocente> centroDocenteList = centroDocenteRepository.findAll();
        assertThat(centroDocenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
