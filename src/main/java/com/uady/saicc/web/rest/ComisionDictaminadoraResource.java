package com.uady.saicc.web.rest;

import com.uady.saicc.repository.ComisionDictaminadoraRepository;
import com.uady.saicc.service.ComisionDictaminadoraService;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.uady.saicc.domain.ComisionDictaminadora}.
 */
@RestController
@RequestMapping("/api")
public class ComisionDictaminadoraResource {

    private final Logger log = LoggerFactory.getLogger(ComisionDictaminadoraResource.class);

    private static final String ENTITY_NAME = "comisionDictaminadora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComisionDictaminadoraService comisionDictaminadoraService;

    private final ComisionDictaminadoraRepository comisionDictaminadoraRepository;

    public ComisionDictaminadoraResource(
        ComisionDictaminadoraService comisionDictaminadoraService,
        ComisionDictaminadoraRepository comisionDictaminadoraRepository
    ) {
        this.comisionDictaminadoraService = comisionDictaminadoraService;
        this.comisionDictaminadoraRepository = comisionDictaminadoraRepository;
    }

    /**
     * {@code POST  /comision-dictaminadoras} : Create a new comisionDictaminadora.
     *
     * @param comisionDictaminadoraDTO the comisionDictaminadoraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comisionDictaminadoraDTO, or with status {@code 400 (Bad Request)} if the comisionDictaminadora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comision-dictaminadoras")
    public ResponseEntity<ComisionDictaminadoraDTO> createComisionDictaminadora(
        @Valid @RequestBody ComisionDictaminadoraDTO comisionDictaminadoraDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ComisionDictaminadora : {}", comisionDictaminadoraDTO);
        if (comisionDictaminadoraDTO.getId() != null) {
            throw new BadRequestAlertException("A new comisionDictaminadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComisionDictaminadoraDTO result = comisionDictaminadoraService.save(comisionDictaminadoraDTO);
        return ResponseEntity
            .created(new URI("/api/comision-dictaminadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comision-dictaminadoras/:id} : Updates an existing comisionDictaminadora.
     *
     * @param id the id of the comisionDictaminadoraDTO to save.
     * @param comisionDictaminadoraDTO the comisionDictaminadoraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comisionDictaminadoraDTO,
     * or with status {@code 400 (Bad Request)} if the comisionDictaminadoraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comisionDictaminadoraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comision-dictaminadoras/{id}")
    public ResponseEntity<ComisionDictaminadoraDTO> updateComisionDictaminadora(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComisionDictaminadoraDTO comisionDictaminadoraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComisionDictaminadora : {}, {}", id, comisionDictaminadoraDTO);
        if (comisionDictaminadoraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comisionDictaminadoraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comisionDictaminadoraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComisionDictaminadoraDTO result = comisionDictaminadoraService.update(comisionDictaminadoraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comisionDictaminadoraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comision-dictaminadoras/:id} : Partial updates given fields of an existing comisionDictaminadora, field will ignore if it is null
     *
     * @param id the id of the comisionDictaminadoraDTO to save.
     * @param comisionDictaminadoraDTO the comisionDictaminadoraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comisionDictaminadoraDTO,
     * or with status {@code 400 (Bad Request)} if the comisionDictaminadoraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the comisionDictaminadoraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the comisionDictaminadoraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comision-dictaminadoras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComisionDictaminadoraDTO> partialUpdateComisionDictaminadora(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComisionDictaminadoraDTO comisionDictaminadoraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComisionDictaminadora partially : {}, {}", id, comisionDictaminadoraDTO);
        if (comisionDictaminadoraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comisionDictaminadoraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comisionDictaminadoraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComisionDictaminadoraDTO> result = comisionDictaminadoraService.partialUpdate(comisionDictaminadoraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comisionDictaminadoraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comision-dictaminadoras} : get all the comisionDictaminadoras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comisionDictaminadoras in body.
     */
    @GetMapping("/comision-dictaminadoras")
    public List<ComisionDictaminadoraDTO> getAllComisionDictaminadoras() {
        log.debug("REST request to get all ComisionDictaminadoras");
        return comisionDictaminadoraService.findAll();
    }

    /**
     * {@code GET  /comision-dictaminadoras/:id} : get the "id" comisionDictaminadora.
     *
     * @param id the id of the comisionDictaminadoraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comisionDictaminadoraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comision-dictaminadoras/{id}")
    public ResponseEntity<ComisionDictaminadoraDTO> getComisionDictaminadora(@PathVariable Long id) {
        log.debug("REST request to get ComisionDictaminadora : {}", id);
        Optional<ComisionDictaminadoraDTO> comisionDictaminadoraDTO = comisionDictaminadoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(comisionDictaminadoraDTO);
    }

    /**
     * {@code DELETE  /comision-dictaminadoras/:id} : delete the "id" comisionDictaminadora.
     *
     * @param id the id of the comisionDictaminadoraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comision-dictaminadoras/{id}")
    public ResponseEntity<Void> deleteComisionDictaminadora(@PathVariable Long id) {
        log.debug("REST request to delete ComisionDictaminadora : {}", id);
        comisionDictaminadoraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
