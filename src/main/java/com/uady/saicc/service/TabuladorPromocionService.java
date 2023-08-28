package com.uady.saicc.service;

import com.uady.saicc.domain.TabuladorPromocion;
import com.uady.saicc.repository.TabuladorPromocionRepository;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
import com.uady.saicc.service.mapper.TabuladorPromocionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TabuladorPromocion}.
 */
@Service
@Transactional
public class TabuladorPromocionService {

    private final Logger log = LoggerFactory.getLogger(TabuladorPromocionService.class);

    private final TabuladorPromocionRepository tabuladorPromocionRepository;

    private final TabuladorPromocionMapper tabuladorPromocionMapper;

    public TabuladorPromocionService(
        TabuladorPromocionRepository tabuladorPromocionRepository,
        TabuladorPromocionMapper tabuladorPromocionMapper
    ) {
        this.tabuladorPromocionRepository = tabuladorPromocionRepository;
        this.tabuladorPromocionMapper = tabuladorPromocionMapper;
    }

    /**
     * Save a tabuladorPromocion.
     *
     * @param tabuladorPromocionDTO the entity to save.
     * @return the persisted entity.
     */
    public TabuladorPromocionDTO save(TabuladorPromocionDTO tabuladorPromocionDTO) {
        log.debug("Request to save TabuladorPromocion : {}", tabuladorPromocionDTO);
        TabuladorPromocion tabuladorPromocion = tabuladorPromocionMapper.toEntity(tabuladorPromocionDTO);
        tabuladorPromocion = tabuladorPromocionRepository.save(tabuladorPromocion);
        return tabuladorPromocionMapper.toDto(tabuladorPromocion);
    }

    /**
     * Update a tabuladorPromocion.
     *
     * @param tabuladorPromocionDTO the entity to save.
     * @return the persisted entity.
     */
    public TabuladorPromocionDTO update(TabuladorPromocionDTO tabuladorPromocionDTO) {
        log.debug("Request to update TabuladorPromocion : {}", tabuladorPromocionDTO);
        TabuladorPromocion tabuladorPromocion = tabuladorPromocionMapper.toEntity(tabuladorPromocionDTO);
        tabuladorPromocion = tabuladorPromocionRepository.save(tabuladorPromocion);
        return tabuladorPromocionMapper.toDto(tabuladorPromocion);
    }

    /**
     * Partially update a tabuladorPromocion.
     *
     * @param tabuladorPromocionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TabuladorPromocionDTO> partialUpdate(TabuladorPromocionDTO tabuladorPromocionDTO) {
        log.debug("Request to partially update TabuladorPromocion : {}", tabuladorPromocionDTO);

        return tabuladorPromocionRepository
            .findById(tabuladorPromocionDTO.getId())
            .map(existingTabuladorPromocion -> {
                tabuladorPromocionMapper.partialUpdate(existingTabuladorPromocion, tabuladorPromocionDTO);

                return existingTabuladorPromocion;
            })
            .map(tabuladorPromocionRepository::save)
            .map(tabuladorPromocionMapper::toDto);
    }

    /**
     * Get all the tabuladorPromocions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TabuladorPromocionDTO> findAll() {
        log.debug("Request to get all TabuladorPromocions");
        return tabuladorPromocionRepository
            .findAll()
            .stream()
            .map(tabuladorPromocionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tabuladorPromocion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TabuladorPromocionDTO> findOne(Long id) {
        log.debug("Request to get TabuladorPromocion : {}", id);
        return tabuladorPromocionRepository.findById(id).map(tabuladorPromocionMapper::toDto);
    }

    /**
     * Delete the tabuladorPromocion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TabuladorPromocion : {}", id);
        tabuladorPromocionRepository.deleteById(id);
    }
}
