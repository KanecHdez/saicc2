package com.uady.saicc.web.rest;

import com.uady.saicc.repository.PeriodoRepository;
import com.uady.saicc.service.PeriodoService;
import com.uady.saicc.service.dto.PeriodoDTO;
import com.uady.saicc.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.uady.saicc.domain.Periodo}.
 */
@RestController
@RequestMapping("/api")
public class PeriodoResource {

    private final Logger log = LoggerFactory.getLogger(PeriodoResource.class);

    private static final String ENTITY_NAME = "periodo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodoService periodoService;

    private final PeriodoRepository periodoRepository;

    public PeriodoResource(PeriodoService periodoService, PeriodoRepository periodoRepository) {
        this.periodoService = periodoService;
        this.periodoRepository = periodoRepository;
    }

    /**
     * {@code POST  /periodos} : Create a new periodo.
     *
     * @param periodoDTO the periodoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodoDTO, or with status {@code 400 (Bad Request)} if the periodo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periodos")
    public ResponseEntity<PeriodoDTO> createPeriodo(@Valid @RequestBody PeriodoDTO periodoDTO) throws URISyntaxException {
        log.debug("REST request to save Periodo : {}", periodoDTO);
        if (periodoDTO.getId() != null) {
            throw new BadRequestAlertException("A new periodo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodoDTO result = periodoService.save(periodoDTO);
        return ResponseEntity
            .created(new URI("/api/periodos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periodos/:id} : Updates an existing periodo.
     *
     * @param id the id of the periodoDTO to save.
     * @param periodoDTO the periodoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoDTO,
     * or with status {@code 400 (Bad Request)} if the periodoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periodos/{id}")
    public ResponseEntity<PeriodoDTO> updatePeriodo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PeriodoDTO periodoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Periodo : {}, {}", id, periodoDTO);
        if (periodoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PeriodoDTO result = periodoService.update(periodoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /periodos/:id} : Partial updates given fields of an existing periodo, field will ignore if it is null
     *
     * @param id the id of the periodoDTO to save.
     * @param periodoDTO the periodoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodoDTO,
     * or with status {@code 400 (Bad Request)} if the periodoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the periodoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the periodoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/periodos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PeriodoDTO> partialUpdatePeriodo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PeriodoDTO periodoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Periodo partially : {}, {}", id, periodoDTO);
        if (periodoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, periodoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!periodoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PeriodoDTO> result = periodoService.partialUpdate(periodoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /periodos} : get all the periodos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periodos in body.
     */
    @GetMapping("/periodos")
    public ResponseEntity<List<PeriodoDTO>> getAllPeriodos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Periodos");
        Page<PeriodoDTO> page = periodoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /periodos/:id} : get the "id" periodo.
     *
     * @param id the id of the periodoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periodos/{id}")
    public ResponseEntity<PeriodoDTO> getPeriodo(@PathVariable Long id) {
        log.debug("REST request to get Periodo : {}", id);
        Optional<PeriodoDTO> periodoDTO = periodoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodoDTO);
    }

    /**
     * {@code DELETE  /periodos/:id} : delete the "id" periodo.
     *
     * @param id the id of the periodoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periodos/{id}")
    public ResponseEntity<Void> deletePeriodo(@PathVariable Long id) {
        log.debug("REST request to delete Periodo : {}", id);
        periodoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
