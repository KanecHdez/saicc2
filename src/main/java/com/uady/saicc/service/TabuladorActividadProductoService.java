package com.uady.saicc.service;

import com.uady.saicc.domain.TabuladorActividadProducto;
import com.uady.saicc.repository.TabuladorActividadProductoRepository;
import com.uady.saicc.service.dto.TabuladorActividadProductoDTO;
import com.uady.saicc.service.mapper.TabuladorActividadProductoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TabuladorActividadProducto}.
 */
@Service
@Transactional
public class TabuladorActividadProductoService {

    private final Logger log = LoggerFactory.getLogger(TabuladorActividadProductoService.class);

    private final TabuladorActividadProductoRepository tabuladorActividadProductoRepository;

    private final TabuladorActividadProductoMapper tabuladorActividadProductoMapper;

    public TabuladorActividadProductoService(
        TabuladorActividadProductoRepository tabuladorActividadProductoRepository,
        TabuladorActividadProductoMapper tabuladorActividadProductoMapper
    ) {
        this.tabuladorActividadProductoRepository = tabuladorActividadProductoRepository;
        this.tabuladorActividadProductoMapper = tabuladorActividadProductoMapper;
    }

    /**
     * Save a tabuladorActividadProducto.
     *
     * @param tabuladorActividadProductoDTO the entity to save.
     * @return the persisted entity.
     */
    public TabuladorActividadProductoDTO save(TabuladorActividadProductoDTO tabuladorActividadProductoDTO) {
        log.debug("Request to save TabuladorActividadProducto : {}", tabuladorActividadProductoDTO);
        TabuladorActividadProducto tabuladorActividadProducto = tabuladorActividadProductoMapper.toEntity(tabuladorActividadProductoDTO);
        tabuladorActividadProducto = tabuladorActividadProductoRepository.save(tabuladorActividadProducto);
        return tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);
    }

    /**
     * Update a tabuladorActividadProducto.
     *
     * @param tabuladorActividadProductoDTO the entity to save.
     * @return the persisted entity.
     */
    public TabuladorActividadProductoDTO update(TabuladorActividadProductoDTO tabuladorActividadProductoDTO) {
        log.debug("Request to update TabuladorActividadProducto : {}", tabuladorActividadProductoDTO);
        TabuladorActividadProducto tabuladorActividadProducto = tabuladorActividadProductoMapper.toEntity(tabuladorActividadProductoDTO);
        tabuladorActividadProducto = tabuladorActividadProductoRepository.save(tabuladorActividadProducto);
        return tabuladorActividadProductoMapper.toDto(tabuladorActividadProducto);
    }

    /**
     * Partially update a tabuladorActividadProducto.
     *
     * @param tabuladorActividadProductoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TabuladorActividadProductoDTO> partialUpdate(TabuladorActividadProductoDTO tabuladorActividadProductoDTO) {
        log.debug("Request to partially update TabuladorActividadProducto : {}", tabuladorActividadProductoDTO);

        return tabuladorActividadProductoRepository
            .findById(tabuladorActividadProductoDTO.getId())
            .map(existingTabuladorActividadProducto -> {
                tabuladorActividadProductoMapper.partialUpdate(existingTabuladorActividadProducto, tabuladorActividadProductoDTO);

                return existingTabuladorActividadProducto;
            })
            .map(tabuladorActividadProductoRepository::save)
            .map(tabuladorActividadProductoMapper::toDto);
    }

    /**
     * Get all the tabuladorActividadProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TabuladorActividadProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TabuladorActividadProductos");
        return tabuladorActividadProductoRepository.findAll(pageable).map(tabuladorActividadProductoMapper::toDto);
    }

    /**
     * Get all the tabuladorActividadProductos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TabuladorActividadProductoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tabuladorActividadProductoRepository.findAllWithEagerRelationships(pageable).map(tabuladorActividadProductoMapper::toDto);
    }

    /**
     * Get one tabuladorActividadProducto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TabuladorActividadProductoDTO> findOne(Long id) {
        log.debug("Request to get TabuladorActividadProducto : {}", id);
        return tabuladorActividadProductoRepository.findOneWithEagerRelationships(id).map(tabuladorActividadProductoMapper::toDto);
    }

    /**
     * Delete the tabuladorActividadProducto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TabuladorActividadProducto : {}", id);
        tabuladorActividadProductoRepository.deleteById(id);
    }
}
