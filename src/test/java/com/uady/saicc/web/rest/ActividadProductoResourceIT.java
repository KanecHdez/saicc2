package com.uady.saicc.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uady.saicc.IntegrationTest;
import com.uady.saicc.domain.ActividadProducto;
import com.uady.saicc.repository.ActividadProductoRepository;
import com.uady.saicc.service.ActividadProductoService;
import com.uady.saicc.service.dto.ActividadProductoDTO;
import com.uady.saicc.service.mapper.ActividadProductoMapper;
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
 * Integration tests for the {@link ActividadProductoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ActividadProductoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/actividad-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ActividadProductoRepository actividadProductoRepository;

    @Mock
    private ActividadProductoRepository actividadProductoRepositoryMock;

    @Autowired
    private ActividadProductoMapper actividadProductoMapper;

    @Mock
    private ActividadProductoService actividadProductoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActividadProductoMockMvc;

    private ActividadProducto actividadProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActividadProducto createEntity(EntityManager em) {
        ActividadProducto actividadProducto = new ActividadProducto()
            .descripcion(DEFAULT_DESCRIPCION)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return actividadProducto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActividadProducto createUpdatedEntity(EntityManager em) {
        ActividadProducto actividadProducto = new ActividadProducto()
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return actividadProducto;
    }

    @BeforeEach
    public void initTest() {
        actividadProducto = createEntity(em);
    }

    @Test
    @Transactional
    void createActividadProducto() throws Exception {
        int databaseSizeBeforeCreate = actividadProductoRepository.findAll().size();
        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);
        restActividadProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeCreate + 1);
        ActividadProducto testActividadProducto = actividadProductoList.get(actividadProductoList.size() - 1);
        assertThat(testActividadProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActividadProducto.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testActividadProducto.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testActividadProducto.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createActividadProductoWithExistingId() throws Exception {
        // Create the ActividadProducto with an existing ID
        actividadProducto.setId(1L);
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        int databaseSizeBeforeCreate = actividadProductoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActividadProductoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllActividadProductos() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        // Get all the actividadProductoList
        restActividadProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(actividadProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActividadProductosWithEagerRelationshipsIsEnabled() throws Exception {
        when(actividadProductoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActividadProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(actividadProductoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActividadProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(actividadProductoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActividadProductoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(actividadProductoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getActividadProducto() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        // Get the actividadProducto
        restActividadProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, actividadProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(actividadProducto.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActividadProducto() throws Exception {
        // Get the actividadProducto
        restActividadProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActividadProducto() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();

        // Update the actividadProducto
        ActividadProducto updatedActividadProducto = actividadProductoRepository.findById(actividadProducto.getId()).get();
        // Disconnect from session so that the updates on updatedActividadProducto are not directly saved in db
        em.detach(updatedActividadProducto);
        updatedActividadProducto
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(updatedActividadProducto);

        restActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actividadProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
        ActividadProducto testActividadProducto = actividadProductoList.get(actividadProductoList.size() - 1);
        assertThat(testActividadProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActividadProducto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, actividadProductoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActividadProductoWithPatch() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();

        // Update the actividadProducto using partial update
        ActividadProducto partialUpdatedActividadProducto = new ActividadProducto();
        partialUpdatedActividadProducto.setId(actividadProducto.getId());

        partialUpdatedActividadProducto
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActividadProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActividadProducto))
            )
            .andExpect(status().isOk());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
        ActividadProducto testActividadProducto = actividadProductoList.get(actividadProductoList.size() - 1);
        assertThat(testActividadProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testActividadProducto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateActividadProductoWithPatch() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();

        // Update the actividadProducto using partial update
        ActividadProducto partialUpdatedActividadProducto = new ActividadProducto();
        partialUpdatedActividadProducto.setId(actividadProducto.getId());

        partialUpdatedActividadProducto
            .descripcion(UPDATED_DESCRIPCION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActividadProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedActividadProducto))
            )
            .andExpect(status().isOk());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
        ActividadProducto testActividadProducto = actividadProductoList.get(actividadProductoList.size() - 1);
        assertThat(testActividadProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testActividadProducto.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testActividadProducto.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActividadProducto.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, actividadProductoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActividadProducto() throws Exception {
        int databaseSizeBeforeUpdate = actividadProductoRepository.findAll().size();
        actividadProducto.setId(count.incrementAndGet());

        // Create the ActividadProducto
        ActividadProductoDTO actividadProductoDTO = actividadProductoMapper.toDto(actividadProducto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActividadProductoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(actividadProductoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActividadProducto in the database
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActividadProducto() throws Exception {
        // Initialize the database
        actividadProductoRepository.saveAndFlush(actividadProducto);

        int databaseSizeBeforeDelete = actividadProductoRepository.findAll().size();

        // Delete the actividadProducto
        restActividadProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, actividadProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActividadProducto> actividadProductoList = actividadProductoRepository.findAll();
        assertThat(actividadProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
