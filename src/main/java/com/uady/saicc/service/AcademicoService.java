package com.uady.saicc.service;

import com.uady.saicc.domain.Academico;
import com.uady.saicc.repository.AcademicoRepository;
import com.uady.saicc.service.dto.AcademicoDTO;
import com.uady.saicc.service.mapper.AcademicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Academico}.
 */
@Service
@Transactional
public class AcademicoService {

    private final Logger log = LoggerFactory.getLogger(AcademicoService.class);

    private final AcademicoRepository academicoRepository;

    private final AcademicoMapper academicoMapper;

    public AcademicoService(AcademicoRepository academicoRepository, AcademicoMapper academicoMapper) {
        this.academicoRepository = academicoRepository;
        this.academicoMapper = academicoMapper;
    }

    /**
     * Save a academico.
     *
     * @param academicoDTO the entity to save.
     * @return the persisted entity.
     */
    public AcademicoDTO save(AcademicoDTO academicoDTO) {
        log.debug("Request to save Academico : {}", academicoDTO);
        Academico academico = academicoMapper.toEntity(academicoDTO);
        academico = academicoRepository.save(academico);
        return academicoMapper.toDto(academico);
    }

    /**
     * Update a academico.
     *
     * @param academicoDTO the entity to save.
     * @return the persisted entity.
     */
    public AcademicoDTO update(AcademicoDTO academicoDTO) {
        log.debug("Request to update Academico : {}", academicoDTO);
        Academico academico = academicoMapper.toEntity(academicoDTO);
        academico = academicoRepository.save(academico);
        return academicoMapper.toDto(academico);
    }

    /**
     * Partially update a academico.
     *
     * @param academicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AcademicoDTO> partialUpdate(AcademicoDTO academicoDTO) {
        log.debug("Request to partially update Academico : {}", academicoDTO);

        return academicoRepository
            .findById(academicoDTO.getId())
            .map(existingAcademico -> {
                academicoMapper.partialUpdate(existingAcademico, academicoDTO);

                return existingAcademico;
            })
            .map(academicoRepository::save)
            .map(academicoMapper::toDto);
    }

    /**
     * Get all the academicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AcademicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Academicos");
        return academicoRepository.findAll(pageable).map(academicoMapper::toDto);
    }

    /**
     * Get one academico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AcademicoDTO> findOne(Long id) {
        log.debug("Request to get Academico : {}", id);
        return academicoRepository.findById(id).map(academicoMapper::toDto);
    }

    /**
     * Delete the academico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Academico : {}", id);
        academicoRepository.deleteById(id);
    }
}
