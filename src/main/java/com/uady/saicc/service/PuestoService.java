package com.uady.saicc.service;

import com.uady.saicc.domain.Puesto;
import com.uady.saicc.repository.PuestoRepository;
import com.uady.saicc.service.dto.PuestoDTO;
import com.uady.saicc.service.mapper.PuestoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Puesto}.
 */
@Service
@Transactional
public class PuestoService {

    private final Logger log = LoggerFactory.getLogger(PuestoService.class);

    private final PuestoRepository puestoRepository;

    private final PuestoMapper puestoMapper;

    public PuestoService(PuestoRepository puestoRepository, PuestoMapper puestoMapper) {
        this.puestoRepository = puestoRepository;
        this.puestoMapper = puestoMapper;
    }

    /**
     * Save a puesto.
     *
     * @param puestoDTO the entity to save.
     * @return the persisted entity.
     */
    public PuestoDTO save(PuestoDTO puestoDTO) {
        log.debug("Request to save Puesto : {}", puestoDTO);
        Puesto puesto = puestoMapper.toEntity(puestoDTO);
        puesto = puestoRepository.save(puesto);
        return puestoMapper.toDto(puesto);
    }

    /**
     * Update a puesto.
     *
     * @param puestoDTO the entity to save.
     * @return the persisted entity.
     */
    public PuestoDTO update(PuestoDTO puestoDTO) {
        log.debug("Request to update Puesto : {}", puestoDTO);
        Puesto puesto = puestoMapper.toEntity(puestoDTO);
        puesto = puestoRepository.save(puesto);
        return puestoMapper.toDto(puesto);
    }

    /**
     * Partially update a puesto.
     *
     * @param puestoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PuestoDTO> partialUpdate(PuestoDTO puestoDTO) {
        log.debug("Request to partially update Puesto : {}", puestoDTO);

        return puestoRepository
            .findById(puestoDTO.getId())
            .map(existingPuesto -> {
                puestoMapper.partialUpdate(existingPuesto, puestoDTO);

                return existingPuesto;
            })
            .map(puestoRepository::save)
            .map(puestoMapper::toDto);
    }

    /**
     * Get all the puestos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PuestoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Puestos");
        return puestoRepository.findAll(pageable).map(puestoMapper::toDto);
    }

    /**
     * Get one puesto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PuestoDTO> findOne(Long id) {
        log.debug("Request to get Puesto : {}", id);
        return puestoRepository.findById(id).map(puestoMapper::toDto);
    }

    /**
     * Delete the puesto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Puesto : {}", id);
        puestoRepository.deleteById(id);
    }
}
