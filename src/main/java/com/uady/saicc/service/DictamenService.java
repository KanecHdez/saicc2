package com.uady.saicc.service;

import com.uady.saicc.domain.Dictamen;
import com.uady.saicc.repository.DictamenRepository;
import com.uady.saicc.service.dto.DictamenDTO;
import com.uady.saicc.service.mapper.DictamenMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Dictamen}.
 */
@Service
@Transactional
public class DictamenService {

    private final Logger log = LoggerFactory.getLogger(DictamenService.class);

    private final DictamenRepository dictamenRepository;

    private final DictamenMapper dictamenMapper;

    public DictamenService(DictamenRepository dictamenRepository, DictamenMapper dictamenMapper) {
        this.dictamenRepository = dictamenRepository;
        this.dictamenMapper = dictamenMapper;
    }

    /**
     * Save a dictamen.
     *
     * @param dictamenDTO the entity to save.
     * @return the persisted entity.
     */
    public DictamenDTO save(DictamenDTO dictamenDTO) {
        log.debug("Request to save Dictamen : {}", dictamenDTO);
        Dictamen dictamen = dictamenMapper.toEntity(dictamenDTO);
        dictamen = dictamenRepository.save(dictamen);
        return dictamenMapper.toDto(dictamen);
    }

    /**
     * Update a dictamen.
     *
     * @param dictamenDTO the entity to save.
     * @return the persisted entity.
     */
    public DictamenDTO update(DictamenDTO dictamenDTO) {
        log.debug("Request to update Dictamen : {}", dictamenDTO);
        Dictamen dictamen = dictamenMapper.toEntity(dictamenDTO);
        dictamen = dictamenRepository.save(dictamen);
        return dictamenMapper.toDto(dictamen);
    }

    /**
     * Partially update a dictamen.
     *
     * @param dictamenDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DictamenDTO> partialUpdate(DictamenDTO dictamenDTO) {
        log.debug("Request to partially update Dictamen : {}", dictamenDTO);

        return dictamenRepository
            .findById(dictamenDTO.getId())
            .map(existingDictamen -> {
                dictamenMapper.partialUpdate(existingDictamen, dictamenDTO);

                return existingDictamen;
            })
            .map(dictamenRepository::save)
            .map(dictamenMapper::toDto);
    }

    /**
     * Get all the dictamen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DictamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dictamen");
        return dictamenRepository.findAll(pageable).map(dictamenMapper::toDto);
    }

    /**
     * Get all the dictamen with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DictamenDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dictamenRepository.findAllWithEagerRelationships(pageable).map(dictamenMapper::toDto);
    }

    /**
     * Get one dictamen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DictamenDTO> findOne(Long id) {
        log.debug("Request to get Dictamen : {}", id);
        return dictamenRepository.findOneWithEagerRelationships(id).map(dictamenMapper::toDto);
    }

    /**
     * Delete the dictamen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dictamen : {}", id);
        dictamenRepository.deleteById(id);
    }
}
