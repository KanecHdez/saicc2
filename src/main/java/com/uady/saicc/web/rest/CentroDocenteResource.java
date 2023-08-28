package com.uady.saicc.web.rest;

import com.uady.saicc.repository.CentroDocenteRepository;
import com.uady.saicc.service.CentroDocenteService;
import com.uady.saicc.service.dto.CentroDocenteDTO;
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
 * REST controller for managing {@link com.uady.saicc.domain.CentroDocente}.
 */
@RestController
@RequestMapping("/api")
public class CentroDocenteResource {

    private final Logger log = LoggerFactory.getLogger(CentroDocenteResource.class);

    private static final String ENTITY_NAME = "centroDocente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CentroDocenteService centroDocenteService;

    private final CentroDocenteRepository centroDocenteRepository;

    public CentroDocenteResource(CentroDocenteService centroDocenteService, CentroDocenteRepository centroDocenteRepository) {
        this.centroDocenteService = centroDocenteService;
        this.centroDocenteRepository = centroDocenteRepository;
    }

    /**
     * {@code POST  /centro-docentes} : Create a new centroDocente.
     *
     * @param centroDocenteDTO the centroDocenteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centroDocenteDTO, or with status {@code 400 (Bad Request)} if the centroDocente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/centro-docentes")
    public ResponseEntity<CentroDocenteDTO> createCentroDocente(@Valid @RequestBody CentroDocenteDTO centroDocenteDTO)
        throws URISyntaxException {
        log.debug("REST request to save CentroDocente : {}", centroDocenteDTO);
        if (centroDocenteDTO.getId() != null) {
            throw new BadRequestAlertException("A new centroDocente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentroDocenteDTO result = centroDocenteService.save(centroDocenteDTO);
        return ResponseEntity
            .created(new URI("/api/centro-docentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /centro-docentes/:id} : Updates an existing centroDocente.
     *
     * @param id the id of the centroDocenteDTO to save.
     * @param centroDocenteDTO the centroDocenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroDocenteDTO,
     * or with status {@code 400 (Bad Request)} if the centroDocenteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centroDocenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/centro-docentes/{id}")
    public ResponseEntity<CentroDocenteDTO> updateCentroDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CentroDocenteDTO centroDocenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CentroDocente : {}, {}", id, centroDocenteDTO);
        if (centroDocenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroDocenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroDocenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CentroDocenteDTO result = centroDocenteService.update(centroDocenteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroDocenteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /centro-docentes/:id} : Partial updates given fields of an existing centroDocente, field will ignore if it is null
     *
     * @param id the id of the centroDocenteDTO to save.
     * @param centroDocenteDTO the centroDocenteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centroDocenteDTO,
     * or with status {@code 400 (Bad Request)} if the centroDocenteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the centroDocenteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the centroDocenteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/centro-docentes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CentroDocenteDTO> partialUpdateCentroDocente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CentroDocenteDTO centroDocenteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CentroDocente partially : {}, {}", id, centroDocenteDTO);
        if (centroDocenteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, centroDocenteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!centroDocenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CentroDocenteDTO> result = centroDocenteService.partialUpdate(centroDocenteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centroDocenteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /centro-docentes} : get all the centroDocentes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centroDocentes in body.
     */
    @GetMapping("/centro-docentes")
    public ResponseEntity<List<CentroDocenteDTO>> getAllCentroDocentes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of CentroDocentes");
        Page<CentroDocenteDTO> page;
        if (eagerload) {
            page = centroDocenteService.findAllWithEagerRelationships(pageable);
        } else {
            page = centroDocenteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /centro-docentes/:id} : get the "id" centroDocente.
     *
     * @param id the id of the centroDocenteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centroDocenteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/centro-docentes/{id}")
    public ResponseEntity<CentroDocenteDTO> getCentroDocente(@PathVariable Long id) {
        log.debug("REST request to get CentroDocente : {}", id);
        Optional<CentroDocenteDTO> centroDocenteDTO = centroDocenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centroDocenteDTO);
    }

    /**
     * {@code DELETE  /centro-docentes/:id} : delete the "id" centroDocente.
     *
     * @param id the id of the centroDocenteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/centro-docentes/{id}")
    public ResponseEntity<Void> deleteCentroDocente(@PathVariable Long id) {
        log.debug("REST request to delete CentroDocente : {}", id);
        centroDocenteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
