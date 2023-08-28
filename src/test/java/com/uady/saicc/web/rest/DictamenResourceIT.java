package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.Dictamen;
import com.uady.saicc.repository.DictamenRepository;
import com.uady.saicc.service.DictamenService;
import com.uady.saicc.service.dto.DictamenDTO;
import com.uady.saicc.service.mapper.DictamenMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DictamenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DictamenResourceIT {

    private static final Integer DEFAULT_NO_DICTAMEN = 1;
    private static final Integer UPDATED_NO_DICTAMEN = 2;

    private static final LocalDate DEFAULT_FECHA_PROMOCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PROMOCION = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_PUNTOS_ALCANZADOS = 1F;
    private static final Float UPDATED_PUNTOS_ALCANZADOS = 2F;

    private static final Float DEFAULT_PUNTOS_REQUERIDOS = 1F;
    private static final Float UPDATED_PUNTOS_REQUERIDOS = 2F;

    private static final Float DEFAULT_PUNTOS_EXCEDENTES = 1F;
    private static final Float UPDATED_PUNTOS_EXCEDENTES = 2F;

    private static final Float DEFAULT_PUNTOS_FALTANTES = 1F;
    private static final Float UPDATED_PUNTOS_FALTANTES = 2F;

    private static final Float DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR = 1F;
    private static final Float UPDATED_PUNTOS_EXCEDENTES_ANTERIOR = 2F;

    private static final Float DEFAULT_PUNTOS_PUESTO_ACTUAL = 1F;
    private static final Float UPDATED_PUNTOS_PUESTO_ACTUAL = 2F;

    private static final Float DEFAULT_PUNTOS_PUESTO_SOLICITADO = 1F;
    private static final Float UPDATED_PUNTOS_PUESTO_SOLICITADO = 2F;

    private static final Boolean DEFAULT_PROCEDE = false;
    private static final Boolean UPDATED_PROCEDE = true;

    private static final Integer DEFAULT_NUMERO_INSTANCIA = 1;
    private static final Integer UPDATED_NUMERO_INSTANCIA = 2;

    private static final String DEFAULT_FOLIO_HOMOLOGACION = "AAAAAAAAAA";
    private static final String UPDATED_FOLIO_HOMOLOGACION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/dictamen";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DictamenRepository dictamenRepository;

    @Mock
    private DictamenRepository dictamenRepositoryMock;

    @Autowired
    private DictamenMapper dictamenMapper;

    @Mock
    private DictamenService dictamenServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDictamenMockMvc;

    private Dictamen dictamen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictamen createEntity(EntityManager em) {
        Dictamen dictamen = new Dictamen()
            .noDictamen(DEFAULT_NO_DICTAMEN)
            .fechaPromocion(DEFAULT_FECHA_PROMOCION)
            .puntosAlcanzados(DEFAULT_PUNTOS_ALCANZADOS)
            .puntosRequeridos(DEFAULT_PUNTOS_REQUERIDOS)
            .puntosExcedentes(DEFAULT_PUNTOS_EXCEDENTES)
            .puntosFaltantes(DEFAULT_PUNTOS_FALTANTES)
            .puntosExcedentesAnterior(DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR)
            .puntosPuestoActual(DEFAULT_PUNTOS_PUESTO_ACTUAL)
            .puntosPuestoSolicitado(DEFAULT_PUNTOS_PUESTO_SOLICITADO)
            .procede(DEFAULT_PROCEDE)
            .numeroInstancia(DEFAULT_NUMERO_INSTANCIA)
            .folioHomologacion(DEFAULT_FOLIO_HOMOLOGACION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return dictamen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictamen createUpdatedEntity(EntityManager em) {
        Dictamen dictamen = new Dictamen()
            .noDictamen(UPDATED_NO_DICTAMEN)
            .fechaPromocion(UPDATED_FECHA_PROMOCION)
            .puntosAlcanzados(UPDATED_PUNTOS_ALCANZADOS)
            .puntosRequeridos(UPDATED_PUNTOS_REQUERIDOS)
            .puntosExcedentes(UPDATED_PUNTOS_EXCEDENTES)
            .puntosFaltantes(UPDATED_PUNTOS_FALTANTES)
            .puntosExcedentesAnterior(UPDATED_PUNTOS_EXCEDENTES_ANTERIOR)
            .puntosPuestoActual(UPDATED_PUNTOS_PUESTO_ACTUAL)
            .puntosPuestoSolicitado(UPDATED_PUNTOS_PUESTO_SOLICITADO)
            .procede(UPDATED_PROCEDE)
            .numeroInstancia(UPDATED_NUMERO_INSTANCIA)
            .folioHomologacion(UPDATED_FOLIO_HOMOLOGACION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return dictamen;
    }

    @BeforeEach
    public void initTest() {
        dictamen = createEntity(em);
    }

    @Test
    @Transactional
    void createDictamen() throws Exception {
        int databaseSizeBeforeCreate = dictamenRepository.findAll().size();
        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);
        restDictamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dictamenDTO)))
            .andExpect(status().isCreated());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeCreate + 1);
        Dictamen testDictamen = dictamenList.get(dictamenList.size() - 1);
        assertThat(testDictamen.getNoDictamen()).isEqualTo(DEFAULT_NO_DICTAMEN);
        assertThat(testDictamen.getFechaPromocion()).isEqualTo(DEFAULT_FECHA_PROMOCION);
        assertThat(testDictamen.getPuntosAlcanzados()).isEqualTo(DEFAULT_PUNTOS_ALCANZADOS);
        assertThat(testDictamen.getPuntosRequeridos()).isEqualTo(DEFAULT_PUNTOS_REQUERIDOS);
        assertThat(testDictamen.getPuntosExcedentes()).isEqualTo(DEFAULT_PUNTOS_EXCEDENTES);
        assertThat(testDictamen.getPuntosFaltantes()).isEqualTo(DEFAULT_PUNTOS_FALTANTES);
        assertThat(testDictamen.getPuntosExcedentesAnterior()).isEqualTo(DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR);
        assertThat(testDictamen.getPuntosPuestoActual()).isEqualTo(DEFAULT_PUNTOS_PUESTO_ACTUAL);
        assertThat(testDictamen.getPuntosPuestoSolicitado()).isEqualTo(DEFAULT_PUNTOS_PUESTO_SOLICITADO);
        assertThat(testDictamen.getProcede()).isEqualTo(DEFAULT_PROCEDE);
        assertThat(testDictamen.getNumeroInstancia()).isEqualTo(DEFAULT_NUMERO_INSTANCIA);
        assertThat(testDictamen.getFolioHomologacion()).isEqualTo(DEFAULT_FOLIO_HOMOLOGACION);
        assertThat(testDictamen.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDictamen.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDictamen.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createDictamenWithExistingId() throws Exception {
        // Create the Dictamen with an existing ID
        dictamen.setId(1L);
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        int databaseSizeBeforeCreate = dictamenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictamenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dictamenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDictamen() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        // Get all the dictamenList
        restDictamenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictamen.getId().intValue())))
            .andExpect(jsonPath("$.[*].noDictamen").value(hasItem(DEFAULT_NO_DICTAMEN)))
            .andExpect(jsonPath("$.[*].fechaPromocion").value(hasItem(DEFAULT_FECHA_PROMOCION.toString())))
            .andExpect(jsonPath("$.[*].puntosAlcanzados").value(hasItem(DEFAULT_PUNTOS_ALCANZADOS.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosRequeridos").value(hasItem(DEFAULT_PUNTOS_REQUERIDOS.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosExcedentes").value(hasItem(DEFAULT_PUNTOS_EXCEDENTES.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosFaltantes").value(hasItem(DEFAULT_PUNTOS_FALTANTES.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosExcedentesAnterior").value(hasItem(DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosPuestoActual").value(hasItem(DEFAULT_PUNTOS_PUESTO_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].puntosPuestoSolicitado").value(hasItem(DEFAULT_PUNTOS_PUESTO_SOLICITADO.doubleValue())))
            .andExpect(jsonPath("$.[*].procede").value(hasItem(DEFAULT_PROCEDE.booleanValue())))
            .andExpect(jsonPath("$.[*].numeroInstancia").value(hasItem(DEFAULT_NUMERO_INSTANCIA)))
            .andExpect(jsonPath("$.[*].folioHomologacion").value(hasItem(DEFAULT_FOLIO_HOMOLOGACION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDictamenWithEagerRelationshipsIsEnabled() throws Exception {
        when(dictamenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDictamenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(dictamenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDictamenWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dictamenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDictamenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(dictamenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDictamen() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        // Get the dictamen
        restDictamenMockMvc
            .perform(get(ENTITY_API_URL_ID, dictamen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dictamen.getId().intValue()))
            .andExpect(jsonPath("$.noDictamen").value(DEFAULT_NO_DICTAMEN))
            .andExpect(jsonPath("$.fechaPromocion").value(DEFAULT_FECHA_PROMOCION.toString()))
            .andExpect(jsonPath("$.puntosAlcanzados").value(DEFAULT_PUNTOS_ALCANZADOS.doubleValue()))
            .andExpect(jsonPath("$.puntosRequeridos").value(DEFAULT_PUNTOS_REQUERIDOS.doubleValue()))
            .andExpect(jsonPath("$.puntosExcedentes").value(DEFAULT_PUNTOS_EXCEDENTES.doubleValue()))
            .andExpect(jsonPath("$.puntosFaltantes").value(DEFAULT_PUNTOS_FALTANTES.doubleValue()))
            .andExpect(jsonPath("$.puntosExcedentesAnterior").value(DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR.doubleValue()))
            .andExpect(jsonPath("$.puntosPuestoActual").value(DEFAULT_PUNTOS_PUESTO_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.puntosPuestoSolicitado").value(DEFAULT_PUNTOS_PUESTO_SOLICITADO.doubleValue()))
            .andExpect(jsonPath("$.procede").value(DEFAULT_PROCEDE.booleanValue()))
            .andExpect(jsonPath("$.numeroInstancia").value(DEFAULT_NUMERO_INSTANCIA))
            .andExpect(jsonPath("$.folioHomologacion").value(DEFAULT_FOLIO_HOMOLOGACION))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDictamen() throws Exception {
        // Get the dictamen
        restDictamenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDictamen() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();

        // Update the dictamen
        Dictamen updatedDictamen = dictamenRepository.findById(dictamen.getId()).get();
        // Disconnect from session so that the updates on updatedDictamen are not directly saved in db
        em.detach(updatedDictamen);
        updatedDictamen
            .noDictamen(UPDATED_NO_DICTAMEN)
            .fechaPromocion(UPDATED_FECHA_PROMOCION)
            .puntosAlcanzados(UPDATED_PUNTOS_ALCANZADOS)
            .puntosRequeridos(UPDATED_PUNTOS_REQUERIDOS)
            .puntosExcedentes(UPDATED_PUNTOS_EXCEDENTES)
            .puntosFaltantes(UPDATED_PUNTOS_FALTANTES)
            .puntosExcedentesAnterior(UPDATED_PUNTOS_EXCEDENTES_ANTERIOR)
            .puntosPuestoActual(UPDATED_PUNTOS_PUESTO_ACTUAL)
            .puntosPuestoSolicitado(UPDATED_PUNTOS_PUESTO_SOLICITADO)
            .procede(UPDATED_PROCEDE)
            .numeroInstancia(UPDATED_NUMERO_INSTANCIA)
            .folioHomologacion(UPDATED_FOLIO_HOMOLOGACION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        DictamenDTO dictamenDTO = dictamenMapper.toDto(updatedDictamen);

        restDictamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dictamenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
        Dictamen testDictamen = dictamenList.get(dictamenList.size() - 1);
        assertThat(testDictamen.getNoDictamen()).isEqualTo(UPDATED_NO_DICTAMEN);
        assertThat(testDictamen.getFechaPromocion()).isEqualTo(UPDATED_FECHA_PROMOCION);
        assertThat(testDictamen.getPuntosAlcanzados()).isEqualTo(UPDATED_PUNTOS_ALCANZADOS);
        assertThat(testDictamen.getPuntosRequeridos()).isEqualTo(UPDATED_PUNTOS_REQUERIDOS);
        assertThat(testDictamen.getPuntosExcedentes()).isEqualTo(UPDATED_PUNTOS_EXCEDENTES);
        assertThat(testDictamen.getPuntosFaltantes()).isEqualTo(UPDATED_PUNTOS_FALTANTES);
        assertThat(testDictamen.getPuntosExcedentesAnterior()).isEqualTo(UPDATED_PUNTOS_EXCEDENTES_ANTERIOR);
        assertThat(testDictamen.getPuntosPuestoActual()).isEqualTo(UPDATED_PUNTOS_PUESTO_ACTUAL);
        assertThat(testDictamen.getPuntosPuestoSolicitado()).isEqualTo(UPDATED_PUNTOS_PUESTO_SOLICITADO);
        assertThat(testDictamen.getProcede()).isEqualTo(UPDATED_PROCEDE);
        assertThat(testDictamen.getNumeroInstancia()).isEqualTo(UPDATED_NUMERO_INSTANCIA);
        assertThat(testDictamen.getFolioHomologacion()).isEqualTo(UPDATED_FOLIO_HOMOLOGACION);
        assertThat(testDictamen.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDictamen.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDictamen.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dictamenDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dictamenDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDictamenWithPatch() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();

        // Update the dictamen using partial update
        Dictamen partialUpdatedDictamen = new Dictamen();
        partialUpdatedDictamen.setId(dictamen.getId());

        partialUpdatedDictamen
            .noDictamen(UPDATED_NO_DICTAMEN)
            .puntosPuestoActual(UPDATED_PUNTOS_PUESTO_ACTUAL)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restDictamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDictamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDictamen))
            )
            .andExpect(status().isOk());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
        Dictamen testDictamen = dictamenList.get(dictamenList.size() - 1);
        assertThat(testDictamen.getNoDictamen()).isEqualTo(UPDATED_NO_DICTAMEN);
        assertThat(testDictamen.getFechaPromocion()).isEqualTo(DEFAULT_FECHA_PROMOCION);
        assertThat(testDictamen.getPuntosAlcanzados()).isEqualTo(DEFAULT_PUNTOS_ALCANZADOS);
        assertThat(testDictamen.getPuntosRequeridos()).isEqualTo(DEFAULT_PUNTOS_REQUERIDOS);
        assertThat(testDictamen.getPuntosExcedentes()).isEqualTo(DEFAULT_PUNTOS_EXCEDENTES);
        assertThat(testDictamen.getPuntosFaltantes()).isEqualTo(DEFAULT_PUNTOS_FALTANTES);
        assertThat(testDictamen.getPuntosExcedentesAnterior()).isEqualTo(DEFAULT_PUNTOS_EXCEDENTES_ANTERIOR);
        assertThat(testDictamen.getPuntosPuestoActual()).isEqualTo(UPDATED_PUNTOS_PUESTO_ACTUAL);
        assertThat(testDictamen.getPuntosPuestoSolicitado()).isEqualTo(DEFAULT_PUNTOS_PUESTO_SOLICITADO);
        assertThat(testDictamen.getProcede()).isEqualTo(DEFAULT_PROCEDE);
        assertThat(testDictamen.getNumeroInstancia()).isEqualTo(DEFAULT_NUMERO_INSTANCIA);
        assertThat(testDictamen.getFolioHomologacion()).isEqualTo(DEFAULT_FOLIO_HOMOLOGACION);
        assertThat(testDictamen.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDictamen.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDictamen.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDictamenWithPatch() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();

        // Update the dictamen using partial update
        Dictamen partialUpdatedDictamen = new Dictamen();
        partialUpdatedDictamen.setId(dictamen.getId());

        partialUpdatedDictamen
            .noDictamen(UPDATED_NO_DICTAMEN)
            .fechaPromocion(UPDATED_FECHA_PROMOCION)
            .puntosAlcanzados(UPDATED_PUNTOS_ALCANZADOS)
            .puntosRequeridos(UPDATED_PUNTOS_REQUERIDOS)
            .puntosExcedentes(UPDATED_PUNTOS_EXCEDENTES)
            .puntosFaltantes(UPDATED_PUNTOS_FALTANTES)
            .puntosExcedentesAnterior(UPDATED_PUNTOS_EXCEDENTES_ANTERIOR)
            .puntosPuestoActual(UPDATED_PUNTOS_PUESTO_ACTUAL)
            .puntosPuestoSolicitado(UPDATED_PUNTOS_PUESTO_SOLICITADO)
            .procede(UPDATED_PROCEDE)
            .numeroInstancia(UPDATED_NUMERO_INSTANCIA)
            .folioHomologacion(UPDATED_FOLIO_HOMOLOGACION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restDictamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDictamen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDictamen))
            )
            .andExpect(status().isOk());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
        Dictamen testDictamen = dictamenList.get(dictamenList.size() - 1);
        assertThat(testDictamen.getNoDictamen()).isEqualTo(UPDATED_NO_DICTAMEN);
        assertThat(testDictamen.getFechaPromocion()).isEqualTo(UPDATED_FECHA_PROMOCION);
        assertThat(testDictamen.getPuntosAlcanzados()).isEqualTo(UPDATED_PUNTOS_ALCANZADOS);
        assertThat(testDictamen.getPuntosRequeridos()).isEqualTo(UPDATED_PUNTOS_REQUERIDOS);
        assertThat(testDictamen.getPuntosExcedentes()).isEqualTo(UPDATED_PUNTOS_EXCEDENTES);
        assertThat(testDictamen.getPuntosFaltantes()).isEqualTo(UPDATED_PUNTOS_FALTANTES);
        assertThat(testDictamen.getPuntosExcedentesAnterior()).isEqualTo(UPDATED_PUNTOS_EXCEDENTES_ANTERIOR);
        assertThat(testDictamen.getPuntosPuestoActual()).isEqualTo(UPDATED_PUNTOS_PUESTO_ACTUAL);
        assertThat(testDictamen.getPuntosPuestoSolicitado()).isEqualTo(UPDATED_PUNTOS_PUESTO_SOLICITADO);
        assertThat(testDictamen.getProcede()).isEqualTo(UPDATED_PROCEDE);
        assertThat(testDictamen.getNumeroInstancia()).isEqualTo(UPDATED_NUMERO_INSTANCIA);
        assertThat(testDictamen.getFolioHomologacion()).isEqualTo(UPDATED_FOLIO_HOMOLOGACION);
        assertThat(testDictamen.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDictamen.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDictamen.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dictamenDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDictamen() throws Exception {
        int databaseSizeBeforeUpdate = dictamenRepository.findAll().size();
        dictamen.setId(count.incrementAndGet());

        // Create the Dictamen
        DictamenDTO dictamenDTO = dictamenMapper.toDto(dictamen);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDictamenMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dictamenDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dictamen in the database
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDictamen() throws Exception {
        // Initialize the database
        dictamenRepository.saveAndFlush(dictamen);

        int databaseSizeBeforeDelete = dictamenRepository.findAll().size();

        // Delete the dictamen
        restDictamenMockMvc
            .perform(delete(ENTITY_API_URL_ID, dictamen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dictamen> dictamenList = dictamenRepository.findAll();
        assertThat(dictamenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
