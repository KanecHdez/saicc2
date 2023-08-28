package com.uady.saicc.web.rest;

import com.uady.saicc.repository.AcademicoRepository;
import com.uady.saicc.service.AcademicoService;
import com.uady.saicc.service.dto.AcademicoDTO;
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
 * REST controller for managing {@link com.uady.saicc.domain.Academico}.
 */
@RestController
@RequestMapping("/api")
public class AcademicoResource {

    private final Logger log = LoggerFactory.getLogger(AcademicoResource.class);

    private static final String ENTITY_NAME = "academico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicoService academicoService;

    private final AcademicoRepository academicoRepository;

    public AcademicoResource(AcademicoService academicoService, AcademicoRepository academicoRepository) {
        this.academicoService = academicoService;
        this.academicoRepository = academicoRepository;
    }

    /**
     * {@code POST  /academicos} : Create a new academico.
     *
     * @param academicoDTO the academicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicoDTO, or with status {@code 400 (Bad Request)} if the academico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academicos")
    public ResponseEntity<AcademicoDTO> createAcademico(@Valid @RequestBody AcademicoDTO academicoDTO) throws URISyntaxException {
        log.debug("REST request to save Academico : {}", academicoDTO);
        if (academicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new academico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicoDTO result = academicoService.save(academicoDTO);
        return ResponseEntity
            .created(new URI("/api/academicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academicos/:id} : Updates an existing academico.
     *
     * @param id the id of the academicoDTO to save.
     * @param academicoDTO the academicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicoDTO,
     * or with status {@code 400 (Bad Request)} if the academicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academicos/{id}")
    public ResponseEntity<AcademicoDTO> updateAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcademicoDTO academicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Academico : {}, {}", id, academicoDTO);
        if (academicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicoDTO result = academicoService.update(academicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academicos/:id} : Partial updates given fields of an existing academico, field will ignore if it is null
     *
     * @param id the id of the academicoDTO to save.
     * @param academicoDTO the academicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicoDTO,
     * or with status {@code 400 (Bad Request)} if the academicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the academicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/academicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicoDTO> partialUpdateAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcademicoDTO academicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Academico partially : {}, {}", id, academicoDTO);
        if (academicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicoDTO> result = academicoService.partialUpdate(academicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /academicos} : get all the academicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicos in body.
     */
    @GetMapping("/academicos")
    public ResponseEntity<List<AcademicoDTO>> getAllAcademicos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Academicos");
        Page<AcademicoDTO> page = academicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academicos/:id} : get the "id" academico.
     *
     * @param id the id of the academicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academicos/{id}")
    public ResponseEntity<AcademicoDTO> getAcademico(@PathVariable Long id) {
        log.debug("REST request to get Academico : {}", id);
        Optional<AcademicoDTO> academicoDTO = academicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicoDTO);
    }

    /**
     * {@code DELETE  /academicos/:id} : delete the "id" academico.
     *
     * @param id the id of the academicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academicos/{id}")
    public ResponseEntity<Void> deleteAcademico(@PathVariable Long id) {
        log.debug("REST request to delete Academico : {}", id);
        academicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
