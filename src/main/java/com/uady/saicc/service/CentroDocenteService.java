package com.uady.saicc.service;

import com.uady.saicc.domain.CentroDocente;
import com.uady.saicc.repository.CentroDocenteRepository;
import com.uady.saicc.service.dto.CentroDocenteDTO;
import com.uady.saicc.service.mapper.CentroDocenteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CentroDocente}.
 */
@Service
@Transactional
public class CentroDocenteService {

    private final Logger log = LoggerFactory.getLogger(CentroDocenteService.class);

    private final CentroDocenteRepository centroDocenteRepository;

    private final CentroDocenteMapper centroDocenteMapper;

    public CentroDocenteService(CentroDocenteRepository centroDocenteRepository, CentroDocenteMapper centroDocenteMapper) {
        this.centroDocenteRepository = centroDocenteRepository;
        this.centroDocenteMapper = centroDocenteMapper;
    }

    /**
     * Save a centroDocente.
     *
     * @param centroDocenteDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroDocenteDTO save(CentroDocenteDTO centroDocenteDTO) {
        log.debug("Request to save CentroDocente : {}", centroDocenteDTO);
        CentroDocente centroDocente = centroDocenteMapper.toEntity(centroDocenteDTO);
        centroDocente = centroDocenteRepository.save(centroDocente);
        return centroDocenteMapper.toDto(centroDocente);
    }

    /**
     * Update a centroDocente.
     *
     * @param centroDocenteDTO the entity to save.
     * @return the persisted entity.
     */
    public CentroDocenteDTO update(CentroDocenteDTO centroDocenteDTO) {
        log.debug("Request to update CentroDocente : {}", centroDocenteDTO);
        CentroDocente centroDocente = centroDocenteMapper.toEntity(centroDocenteDTO);
        centroDocente = centroDocenteRepository.save(centroDocente);
        return centroDocenteMapper.toDto(centroDocente);
    }

    /**
     * Partially update a centroDocente.
     *
     * @param centroDocenteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CentroDocenteDTO> partialUpdate(CentroDocenteDTO centroDocenteDTO) {
        log.debug("Request to partially update CentroDocente : {}", centroDocenteDTO);

        return centroDocenteRepository
            .findById(centroDocenteDTO.getId())
            .map(existingCentroDocente -> {
                centroDocenteMapper.partialUpdate(existingCentroDocente, centroDocenteDTO);

                return existingCentroDocente;
            })
            .map(centroDocenteRepository::save)
            .map(centroDocenteMapper::toDto);
    }

    /**
     * Get all the centroDocentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CentroDocenteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentroDocentes");
        return centroDocenteRepository.findAll(pageable).map(centroDocenteMapper::toDto);
    }

    /**
     * Get all the centroDocentes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CentroDocenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return centroDocenteRepository.findAllWithEagerRelationships(pageable).map(centroDocenteMapper::toDto);
    }

    /**
     * Get one centroDocente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CentroDocenteDTO> findOne(Long id) {
        log.debug("Request to get CentroDocente : {}", id);
        return centroDocenteRepository.findOneWithEagerRelationships(id).map(centroDocenteMapper::toDto);
    }

    /**
     * Delete the centroDocente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CentroDocente : {}", id);
        centroDocenteRepository.deleteById(id);
    }
}
