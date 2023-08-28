package com.uady.saicc.service;

import com.uady.saicc.domain.ActividadProducto;
import com.uady.saicc.repository.ActividadProductoRepository;
import com.uady.saicc.service.dto.ActividadProductoDTO;
import com.uady.saicc.service.mapper.ActividadProductoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ActividadProducto}.
 */
@Service
@Transactional
public class ActividadProductoService {

    private final Logger log = LoggerFactory.getLogger(ActividadProductoService.class);

    private final ActividadProductoRepository actividadProductoRepository;

    private final ActividadProductoMapper actividadProductoMapper;

    public ActividadProductoService(
        ActividadProductoRepository actividadProductoRepository,
        ActividadProductoMapper actividadProductoMapper
    ) {
        this.actividadProductoRepository = actividadProductoRepository;
        this.actividadProductoMapper = actividadProductoMapper;
    }

    /**
     * Save a actividadProducto.
     *
     * @param actividadProductoDTO the entity to save.
     * @return the persisted entity.
     */
    public ActividadProductoDTO save(ActividadProductoDTO actividadProductoDTO) {
        log.debug("Request to save ActividadProducto : {}", actividadProductoDTO);
        ActividadProducto actividadProducto = actividadProductoMapper.toEntity(actividadProductoDTO);
        actividadProducto = actividadProductoRepository.save(actividadProducto);
        return actividadProductoMapper.toDto(actividadProducto);
    }

    /**
     * Update a actividadProducto.
     *
     * @param actividadProductoDTO the entity to save.
     * @return the persisted entity.
     */
    public ActividadProductoDTO update(ActividadProductoDTO actividadProductoDTO) {
        log.debug("Request to update ActividadProducto : {}", actividadProductoDTO);
        ActividadProducto actividadProducto = actividadProductoMapper.toEntity(actividadProductoDTO);
        actividadProducto = actividadProductoRepository.save(actividadProducto);
        return actividadProductoMapper.toDto(actividadProducto);
    }

    /**
     * Partially update a actividadProducto.
     *
     * @param actividadProductoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActividadProductoDTO> partialUpdate(ActividadProductoDTO actividadProductoDTO) {
        log.debug("Request to partially update ActividadProducto : {}", actividadProductoDTO);

        return actividadProductoRepository
            .findById(actividadProductoDTO.getId())
            .map(existingActividadProducto -> {
                actividadProductoMapper.partialUpdate(existingActividadProducto, actividadProductoDTO);

                return existingActividadProducto;
            })
            .map(actividadProductoRepository::save)
            .map(actividadProductoMapper::toDto);
    }

    /**
     * Get all the actividadProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActividadProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActividadProductos");
        return actividadProductoRepository.findAll(pageable).map(actividadProductoMapper::toDto);
    }

    /**
     * Get all the actividadProductos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ActividadProductoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return actividadProductoRepository.findAllWithEagerRelationships(pageable).map(actividadProductoMapper::toDto);
    }

    /**
     * Get one actividadProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActividadProductoDTO> findOne(Long id) {
        log.debug("Request to get ActividadProducto : {}", id);
        return actividadProductoRepository.findOneWithEagerRelationships(id).map(actividadProductoMapper::toDto);
    }

    /**
     * Delete the actividadProducto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActividadProducto : {}", id);
        actividadProductoRepository.deleteById(id);
    }
}
