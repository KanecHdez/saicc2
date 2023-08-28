package com.uady.saicc.service;

import com.uady.saicc.domain.Periodo;
import com.uady.saicc.repository.PeriodoRepository;
import com.uady.saicc.service.dto.PeriodoDTO;
import com.uady.saicc.service.mapper.PeriodoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Periodo}.
 */
@Service
@Transactional
public class PeriodoService {

    private final Logger log = LoggerFactory.getLogger(PeriodoService.class);

    private final PeriodoRepository periodoRepository;

    private final PeriodoMapper periodoMapper;

    public PeriodoService(PeriodoRepository periodoRepository, PeriodoMapper periodoMapper) {
        this.periodoRepository = periodoRepository;
        this.periodoMapper = periodoMapper;
    }

    /**
     * Save a periodo.
     *
     * @param periodoDTO the entity to save.
     * @return the persisted entity.
     */
    public PeriodoDTO save(PeriodoDTO periodoDTO) {
        log.debug("Request to save Periodo : {}", periodoDTO);
        Periodo periodo = periodoMapper.toEntity(periodoDTO);
        periodo = periodoRepository.save(periodo);
        return periodoMapper.toDto(periodo);
    }

    /**
     * Update a periodo.
     *
     * @param periodoDTO the entity to save.
     * @return the persisted entity.
     */
    public PeriodoDTO update(PeriodoDTO periodoDTO) {
        log.debug("Request to update Periodo : {}", periodoDTO);
        Periodo periodo = periodoMapper.toEntity(periodoDTO);
        periodo = periodoRepository.save(periodo);
        return periodoMapper.toDto(periodo);
    }

    /**
     * Partially update a periodo.
     *
     * @param periodoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PeriodoDTO> partialUpdate(PeriodoDTO periodoDTO) {
        log.debug("Request to partially update Periodo : {}", periodoDTO);

        return periodoRepository
            .findById(periodoDTO.getId())
            .map(existingPeriodo -> {
                periodoMapper.partialUpdate(existingPeriodo, periodoDTO);

                return existingPeriodo;
            })
            .map(periodoRepository::save)
            .map(periodoMapper::toDto);
    }

    /**
     * Get all the periodos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periodos");
        return periodoRepository.findAll(pageable).map(periodoMapper::toDto);
    }

    /**
     * Get one periodo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PeriodoDTO> findOne(Long id) {
        log.debug("Request to get Periodo : {}", id);
        return periodoRepository.findById(id).map(periodoMapper::toDto);
    }

    /**
     * Delete the periodo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Periodo : {}", id);
        periodoRepository.deleteById(id);
    }
}
