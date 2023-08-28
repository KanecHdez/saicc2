package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.Academico;
import com.uady.saicc.repository.AcademicoRepository;
import com.uady.saicc.service.dto.AcademicoDTO;
import com.uady.saicc.service.mapper.AcademicoMapper;
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
 * Integration tests for the {@link AcademicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicoResourceIT {

    private static final Integer DEFAULT_CVE_EMPLEADO = 1;
    private static final Integer UPDATED_CVE_EMPLEADO = 2;

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/academicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcademicoRepository academicoRepository;

    @Autowired
    private AcademicoMapper academicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicoMockMvc;

    private Academico academico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Academico createEntity(EntityManager em) {
        Academico academico = new Academico()
            .cveEmpleado(DEFAULT_CVE_EMPLEADO)
            .nombres(DEFAULT_NOMBRES)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return academico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Academico createUpdatedEntity(EntityManager em) {
        Academico academico = new Academico()
            .cveEmpleado(UPDATED_CVE_EMPLEADO)
            .nombres(UPDATED_NOMBRES)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return academico;
    }

    @BeforeEach
    public void initTest() {
        academico = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademico() throws Exception {
        int databaseSizeBeforeCreate = academicoRepository.findAll().size();
        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);
        restAcademicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeCreate + 1);
        Academico testAcademico = academicoList.get(academicoList.size() - 1);
        assertThat(testAcademico.getCveEmpleado()).isEqualTo(DEFAULT_CVE_EMPLEADO);
        assertThat(testAcademico.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testAcademico.getPrimerApellido()).isEqualTo(DEFAULT_PRIMER_APELLIDO);
        assertThat(testAcademico.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testAcademico.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAcademico.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAcademico.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createAcademicoWithExistingId() throws Exception {
        // Create the Academico with an existing ID
        academico.setId(1L);
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        int databaseSizeBeforeCreate = academicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcademicos() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        // Get all the academicoList
        restAcademicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academico.getId().intValue())))
            .andExpect(jsonPath("$.[*].cveEmpleado").value(hasItem(DEFAULT_CVE_EMPLEADO)))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAcademico() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        // Get the academico
        restAcademicoMockMvc
            .perform(get(ENTITY_API_URL_ID, academico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academico.getId().intValue()))
            .andExpect(jsonPath("$.cveEmpleado").value(DEFAULT_CVE_EMPLEADO))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAcademico() throws Exception {
        // Get the academico
        restAcademicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAcademico() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();

        // Update the academico
        Academico updatedAcademico = academicoRepository.findById(academico.getId()).get();
        // Disconnect from session so that the updates on updatedAcademico are not directly saved in db
        em.detach(updatedAcademico);
        updatedAcademico
            .cveEmpleado(UPDATED_CVE_EMPLEADO)
            .nombres(UPDATED_NOMBRES)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        AcademicoDTO academicoDTO = academicoMapper.toDto(updatedAcademico);

        restAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
        Academico testAcademico = academicoList.get(academicoList.size() - 1);
        assertThat(testAcademico.getCveEmpleado()).isEqualTo(UPDATED_CVE_EMPLEADO);
        assertThat(testAcademico.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testAcademico.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testAcademico.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testAcademico.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAcademico.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAcademico.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicoWithPatch() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();

        // Update the academico using partial update
        Academico partialUpdatedAcademico = new Academico();
        partialUpdatedAcademico.setId(academico.getId());

        partialUpdatedAcademico
            .cveEmpleado(UPDATED_CVE_EMPLEADO)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademico))
            )
            .andExpect(status().isOk());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
        Academico testAcademico = academicoList.get(academicoList.size() - 1);
        assertThat(testAcademico.getCveEmpleado()).isEqualTo(UPDATED_CVE_EMPLEADO);
        assertThat(testAcademico.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testAcademico.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testAcademico.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testAcademico.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAcademico.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAcademico.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAcademicoWithPatch() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();

        // Update the academico using partial update
        Academico partialUpdatedAcademico = new Academico();
        partialUpdatedAcademico.setId(academico.getId());

        partialUpdatedAcademico
            .cveEmpleado(UPDATED_CVE_EMPLEADO)
            .nombres(UPDATED_NOMBRES)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademico))
            )
            .andExpect(status().isOk());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
        Academico testAcademico = academicoList.get(academicoList.size() - 1);
        assertThat(testAcademico.getCveEmpleado()).isEqualTo(UPDATED_CVE_EMPLEADO);
        assertThat(testAcademico.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testAcademico.getPrimerApellido()).isEqualTo(UPDATED_PRIMER_APELLIDO);
        assertThat(testAcademico.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testAcademico.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAcademico.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAcademico.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademico() throws Exception {
        int databaseSizeBeforeUpdate = academicoRepository.findAll().size();
        academico.setId(count.incrementAndGet());

        // Create the Academico
        AcademicoDTO academicoDTO = academicoMapper.toDto(academico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(academicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Academico in the database
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademico() throws Exception {
        // Initialize the database
        academicoRepository.saveAndFlush(academico);

        int databaseSizeBeforeDelete = academicoRepository.findAll().size();

        // Delete the academico
        restAcademicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, academico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Academico> academicoList = academicoRepository.findAll();
        assertThat(academicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
