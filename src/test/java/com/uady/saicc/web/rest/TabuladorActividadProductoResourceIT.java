package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.TabuladorActividadProducto;
import com.uady.saicc.repository.TabuladorActividadProductoRepository;
import com.uady.saicc.service.TabuladorActividadProductoService;
import com.uady.saicc.service.dto.TabuladorActividadProductoDTO;
import com.uady.saicc.service.mapper.TabuladorActividadProductoMapper;
import java.time.Instant;
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
 * Integration tests for the {@link TabuladorActividadProductoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TabuladorActividadProductoResourceIT {

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CVE_TAB_PROM = 1;
    private static final Integer UPDATED_CVE_TAB_PROM = 2;

    private static final Integer DEFAULT_NIVEL = 1;
    private static final Integer UPDATED_NIVEL = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_INGRESO_MINIMO = 1;
    private static final Integer UPDATED_INGRESO_MINIMO = 2;

    private static final Integer DEFAULT_INGRESO_MAXIMO = 1;
    private static final Integer UPDATED_INGRESO_MAXIMO = 2;

    private static final Integer DEFAULT_PUNTOS_MAXIMOS = 1;
    private static final Integer UPDATED_PUNTOS_MAXIMOS = 2;

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tabulador-actividad-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TabuladorActividadProductoRepository tabuladorActividadProductoRepository;

    @Mock
    private TabuladorActividadProductoRepository tabuladorActividadProductoRepositoryMock;

    @Autowired
    private TabuladorActividadProductoMapper tabuladorActividadProductoMapper;

    @Mock
    private TabuladorActividadProductoService tabuladorActividadProductoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTabuladorActividadProductoMockMvc;

    private TabuladorActividadProducto tabuladorActividadProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabuladorActividadProducto createEntity(EntityManager em) {
        TabuladorActividadProducto tabuladorActividadProducto = new TabuladorActividadProducto()
            .clave(DEFAULT_CLAVE)
            .cveTabProm(DEFAULT_CVE_TAB_PROM)
            .nivel(DEFAULT_NIVEL)
            .descripcion(DEFAULT_DESCRIPCION)
            .ingresoMinimo(DEFAULT_INGRESO_MINIMO)
            .ingresoMaximo(DEFAULT_INGRESO_MAXIMO)
            .puntosMaximos(DEFAULT_PUNTOS_MAXIMOS)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return tabuladorActividadProducto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabuladorActividadProducto createUpdatedEntity(EntityManager em) {
        TabuladorActividadProducto tabuladorActividadProducto = new TabuladorActividadProducto()
            .clave(UPDATED_CLAVE)
            .cveTabProm(UPDATED_CVE_TAB_PROM)
            .nivel(UPDATED_NIVEL)
            .descripcion(UPDATED_DESCRIPCION)
            .ingresoMinimo(UPDATED_INGRESO_MINIMO)
            .ingresoMaximo(UPDATED_INGRESO_MAXIMO)
            .puntosMaximos(UPDATED_PUNTOS_MAXIMOS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return tabuladorActividadProducto;
    }

    @BeforeEach
    public void initTest() {
        tabuladorActividadProducto = createEntity(em);
    }

    @Test
    @Transactional
    void createTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeCreate = tabuladorActividadProductoRepository.findAll().size();
        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);
        restTabuladorActividadProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeCreate + 1);
        TabuladorActividadProducto testTabuladorActividadProducto = tabuladorActividadProductoList.get(
            tabuladorActividadProductoList.size() - 1
        );
        assertThat(testTabuladorActividadProducto.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTabuladorActividadProducto.getCveTabProm()).isEqualTo(DEFAULT_CVE_TAB_PROM);
        assertThat(testTabuladorActividadProducto.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testTabuladorActividadProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTabuladorActividadProducto.getIngresoMinimo()).isEqualTo(DEFAULT_INGRESO_MINIMO);
        assertThat(testTabuladorActividadProducto.getIngresoMaximo()).isEqualTo(DEFAULT_INGRESO_MAXIMO);
        assertThat(testTabuladorActividadProducto.getPuntosMaximos()).isEqualTo(DEFAULT_PUNTOS_MAXIMOS);
        assertThat(testTabuladorActividadProducto.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTabuladorActividadProducto.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTabuladorActividadProducto.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTabuladorActividadProductoWithExistingId() throws Exception {
        // Create the TabuladorActividadProducto with an existing ID
        tabuladorActividadProducto.setId(1L);
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        int databaseSizeBeforeCreate = tabuladorActividadProductoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabuladorActividadProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTabuladorActividadProductos() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        // Get all the tabuladorActividadProductoList
        restTabuladorActividadProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabuladorActividadProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].cveTabProm").value(hasItem(DEFAULT_CVE_TAB_PROM)))
            .andExpect(jsonPath("$.[*].nivel").value(hasItem(DEFAULT_NIVEL)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].ingresoMinimo").value(hasItem(DEFAULT_INGRESO_MINIMO)))
            .andExpect(jsonPath("$.[*].ingresoMaximo").value(hasItem(DEFAULT_INGRESO_MAXIMO)))
            .andExpect(jsonPath("$.[*].puntosMaximos").value(hasItem(DEFAULT_PUNTOS_MAXIMOS)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTabuladorActividadProductosWithEagerRelationshipsIsEnabled() throws Exception {
        when(tabuladorActividadProductoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTabuladorActividadProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tabuladorActividadProductoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTabuladorActividadProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tabuladorActividadProductoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTabuladorActividadProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tabuladorActividadProductoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTabuladorActividadProducto() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        // Get the tabuladorActividadProducto
        restTabuladorActividadProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, tabuladorActividadProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tabuladorActividadProducto.getId().intValue()))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.cveTabProm").value(DEFAULT_CVE_TAB_PROM))
            .andExpect(jsonPath("$.nivel").value(DEFAULT_NIVEL))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.ingresoMinimo").value(DEFAULT_INGRESO_MINIMO))
            .andExpect(jsonPath("$.ingresoMaximo").value(DEFAULT_INGRESO_MAXIMO))
            .andExpect(jsonPath("$.puntosMaximos").value(DEFAULT_PUNTOS_MAXIMOS))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTabuladorActividadProducto() throws Exception {
        // Get the tabuladorActividadProducto
        restTabuladorActividadProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTabuladorActividadProducto() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();

        // Update the tabuladorActividadProducto
        TabuladorActividadProducto updatedTabuladorActividadProducto = tabuladorActividadProductoRepository
            .findById(tabuladorActividadProducto.getId())
            .get();
        // Disconnect from session so that the updates on updatedTabuladorActividadProducto are not directly saved in db
        em.detach(updatedTabuladorActividadProducto);
        updatedTabuladorActividadProducto
            .clave(UPDATED_CLAVE)
            .cveTabProm(UPDATED_CVE_TAB_PROM)
            .nivel(UPDATED_NIVEL)
            .descripcion(UPDATED_DESCRIPCION)
            .ingresoMinimo(UPDATED_INGRESO_MINIMO)
            .ingresoMaximo(UPDATED_INGRESO_MAXIMO)
            .puntosMaximos(UPDATED_PUNTOS_MAXIMOS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(
            updatedTabuladorActividadProducto
        );

        restTabuladorActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabuladorActividadProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
        TabuladorActividadProducto testTabuladorActividadProducto = tabuladorActividadProductoList.get(
            tabuladorActividadProductoList.size() - 1
        );
        assertThat(testTabuladorActividadProducto.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTabuladorActividadProducto.getCveTabProm()).isEqualTo(UPDATED_CVE_TAB_PROM);
        assertThat(testTabuladorActividadProducto.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testTabuladorActividadProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTabuladorActividadProducto.getIngresoMinimo()).isEqualTo(UPDATED_INGRESO_MINIMO);
        assertThat(testTabuladorActividadProducto.getIngresoMaximo()).isEqualTo(UPDATED_INGRESO_MAXIMO);
        assertThat(testTabuladorActividadProducto.getPuntosMaximos()).isEqualTo(UPDATED_PUNTOS_MAXIMOS);
        assertThat(testTabuladorActividadProducto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTabuladorActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tabuladorActividadProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTabuladorActividadProductoWithPatch() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();

        // Update the tabuladorActividadProducto using partial update
        TabuladorActividadProducto partialUpdatedTabuladorActividadProducto = new TabuladorActividadProducto();
        partialUpdatedTabuladorActividadProducto.setId(tabuladorActividadProducto.getId());

        partialUpdatedTabuladorActividadProducto
            .clave(UPDATED_CLAVE)
            .cveTabProm(UPDATED_CVE_TAB_PROM)
            .descripcion(UPDATED_DESCRIPCION)
            .ingresoMaximo(UPDATED_INGRESO_MAXIMO)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTabuladorActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabuladorActividadProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabuladorActividadProducto))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
        TabuladorActividadProducto testTabuladorActividadProducto = tabuladorActividadProductoList.get(
            tabuladorActividadProductoList.size() - 1
        );
        assertThat(testTabuladorActividadProducto.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTabuladorActividadProducto.getCveTabProm()).isEqualTo(UPDATED_CVE_TAB_PROM);
        assertThat(testTabuladorActividadProducto.getNivel()).isEqualTo(DEFAULT_NIVEL);
        assertThat(testTabuladorActividadProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTabuladorActividadProducto.getIngresoMinimo()).isEqualTo(DEFAULT_INGRESO_MINIMO);
        assertThat(testTabuladorActividadProducto.getIngresoMaximo()).isEqualTo(UPDATED_INGRESO_MAXIMO);
        assertThat(testTabuladorActividadProducto.getPuntosMaximos()).isEqualTo(DEFAULT_PUNTOS_MAXIMOS);
        assertThat(testTabuladorActividadProducto.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTabuladorActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTabuladorActividadProductoWithPatch() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();

        // Update the tabuladorActividadProducto using partial update
        TabuladorActividadProducto partialUpdatedTabuladorActividadProducto = new TabuladorActividadProducto();
        partialUpdatedTabuladorActividadProducto.setId(tabuladorActividadProducto.getId());

        partialUpdatedTabuladorActividadProducto
            .clave(UPDATED_CLAVE)
            .cveTabProm(UPDATED_CVE_TAB_PROM)
            .nivel(UPDATED_NIVEL)
            .descripcion(UPDATED_DESCRIPCION)
            .ingresoMinimo(UPDATED_INGRESO_MINIMO)
            .ingresoMaximo(UPDATED_INGRESO_MAXIMO)
            .puntosMaximos(UPDATED_PUNTOS_MAXIMOS)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restTabuladorActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTabuladorActividadProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTabuladorActividadProducto))
            )
            .andExpect(status().isOk());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
        TabuladorActividadProducto testTabuladorActividadProducto = tabuladorActividadProductoList.get(
            tabuladorActividadProductoList.size() - 1
        );
        assertThat(testTabuladorActividadProducto.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTabuladorActividadProducto.getCveTabProm()).isEqualTo(UPDATED_CVE_TAB_PROM);
        assertThat(testTabuladorActividadProducto.getNivel()).isEqualTo(UPDATED_NIVEL);
        assertThat(testTabuladorActividadProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTabuladorActividadProducto.getIngresoMinimo()).isEqualTo(UPDATED_INGRESO_MINIMO);
        assertThat(testTabuladorActividadProducto.getIngresoMaximo()).isEqualTo(UPDATED_INGRESO_MAXIMO);
        assertThat(testTabuladorActividadProducto.getPuntosMaximos()).isEqualTo(UPDATED_PUNTOS_MAXIMOS);
        assertThat(testTabuladorActividadProducto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTabuladorActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTabuladorActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tabuladorActividadProductoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTabuladorActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = tabuladorActividadProductoRepository.findAll().size();
        tabuladorActividadProducto.setId(count.incrementAndGet());

        // Create the TabuladorActividadProducto
        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTabuladorActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tabuladorActividadProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TabuladorActividadProducto in the database
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTabuladorActividadProducto() throws Exception {
        // Initialize the database
        tabuladorActividadProductoRepository.saveAndFlush(tabuladorActividadProducto);

        int databaseSizeBeforeDelete = tabuladorActividadProductoRepository.findAll().size();

        // Delete the tabuladorActividadProducto
        restTabuladorActividadProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tabuladorActividadProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TabuladorActividadProducto> tabuladorActividadProductoList = tabuladorActividadProductoRepository.findAll();
        assertThat(tabuladorActividadProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
