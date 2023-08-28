package com.uady.saicc.service;

import com.uady.saicc.domain.ComisionDictaminadora;
import com.uady.saicc.repository.ComisionDictaminadoraRepository;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
import com.uady.saicc.service.mapper.ComisionDictaminadoraMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComisionDictaminadora}.
 */
@Service
@Transactional
public class ComisionDictaminadoraService {

    private final Logger log = LoggerFactory.getLogger(ComisionDictaminadoraService.class);

    private final ComisionDictaminadoraRepository comisionDictaminadoraRepository;

    private final ComisionDictaminadoraMapper comisionDictaminadoraMapper;

    public ComisionDictaminadoraService(
        ComisionDictaminadoraRepository comisionDictaminadoraRepository,
        ComisionDictaminadoraMapper comisionDictaminadoraMapper
    ) {
        this.comisionDictaminadoraRepository = comisionDictaminadoraRepository;
        this.comisionDictaminadoraMapper = comisionDictaminadoraMapper;
    }

    /**
     * Save a comisionDictaminadora.
     *
     * @param comisionDictaminadoraDTO the entity to save.
     * @return the persisted entity.
     */
    public ComisionDictaminadoraDTO save(ComisionDictaminadoraDTO comisionDictaminadoraDTO) {
        log.debug("Request to save ComisionDictaminadora : {}", comisionDictaminadoraDTO);
        ComisionDictaminadora comisionDictaminadora = comisionDictaminadoraMapper.toEntity(comisionDictaminadoraDTO);
        comisionDictaminadora = comisionDictaminadoraRepository.save(comisionDictaminadora);
        return comisionDictaminadoraMapper.toDto(comisionDictaminadora);
    }

    /**
     * Update a comisionDictaminadora.
     *
     * @param comisionDictaminadoraDTO the entity to save.
     * @return the persisted entity.
     */
    public ComisionDictaminadoraDTO update(ComisionDictaminadoraDTO comisionDictaminadoraDTO) {
        log.debug("Request to update ComisionDictaminadora : {}", comisionDictaminadoraDTO);
        ComisionDictaminadora comisionDictaminadora = comisionDictaminadoraMapper.toEntity(comisionDictaminadoraDTO);
        comisionDictaminadora = comisionDictaminadoraRepository.save(comisionDictaminadora);
        return comisionDictaminadoraMapper.toDto(comisionDictaminadora);
    }

    /**
     * Partially update a comisionDictaminadora.
     *
     * @param comisionDictaminadoraDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComisionDictaminadoraDTO> partialUpdate(ComisionDictaminadoraDTO comisionDictaminadoraDTO) {
        log.debug("Request to partially update ComisionDictaminadora : {}", comisionDictaminadoraDTO);

        return comisionDictaminadoraRepository
            .findById(comisionDictaminadoraDTO.getId())
            .map(existingComisionDictaminadora -> {
                comisionDictaminadoraMapper.partialUpdate(existingComisionDictaminadora, comisionDictaminadoraDTO);

                return existingComisionDictaminadora;
            })
            .map(comisionDictaminadoraRepository::save)
            .map(comisionDictaminadoraMapper::toDto);
    }

    /**
     * Get all the comisionDictaminadoras.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ComisionDictaminadoraDTO> findAll() {
        log.debug("Request to get all ComisionDictaminadoras");
        return comisionDictaminadoraRepository
            .findAll()
            .stream()
            .map(comisionDictaminadoraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one comisionDictaminadora by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComisionDictaminadoraDTO> findOne(Long id) {
        log.debug("Request to get ComisionDictaminadora : {}", id);
        return comisionDictaminadoraRepository.findById(id).map(comisionDictaminadoraMapper::toDto);
    }

    /**
     * Delete the comisionDictaminadora by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComisionDictaminadora : {}", id);
        comisionDictaminadoraRepository.deleteById(id);
    }
}
