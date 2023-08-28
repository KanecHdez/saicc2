package com.uady.saicc.web.rest;

import com.uady.saicc.repository.TabuladorPromocionRepository;
import com.uady.saicc.service.TabuladorPromocionService;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
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
 * REST controller for managing {@link com.uady.saicc.domain.TabuladorPromocion}.
 */
@RestController
@RequestMapping("/api")
public class TabuladorPromocionResource {

    private final Logger log = LoggerFactory.getLogger(TabuladorPromocionResource.class);

    private static final String ENTITY_NAME = "tabuladorPromocion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TabuladorPromocionService tabuladorPromocionService;

    private final TabuladorPromocionRepository tabuladorPromocionRepository;

    public TabuladorPromocionResource(
        TabuladorPromocionService tabuladorPromocionService,
        TabuladorPromocionRepository tabuladorPromocionRepository
    ) {
        this.tabuladorPromocionService = tabuladorPromocionService;
        this.tabuladorPromocionRepository = tabuladorPromocionRepository;
    }

    /**
     * {@code POST  /tabulador-promocions} : Create a new tabuladorPromocion.
     *
     * @param tabuladorPromocionDTO the tabuladorPromocionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tabuladorPromocionDTO, or with status {@code 400 (Bad Request)} if the tabuladorPromocion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tabulador-promocions")
    public ResponseEntity<TabuladorPromocionDTO> createTabuladorPromocion(@Valid @RequestBody TabuladorPromocionDTO tabuladorPromocionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TabuladorPromocion : {}", tabuladorPromocionDTO);
        if (tabuladorPromocionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tabuladorPromocion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabuladorPromocionDTO result = tabuladorPromocionService.save(tabuladorPromocionDTO);
        return ResponseEntity
            .created(new URI("/api/tabulador-promocions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tabulador-promocions/:id} : Updates an existing tabuladorPromocion.
     *
     * @param id the id of the tabuladorPromocionDTO to save.
     * @param tabuladorPromocionDTO the tabuladorPromocionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabuladorPromocionDTO,
     * or with status {@code 400 (Bad Request)} if the tabuladorPromocionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tabuladorPromocionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tabulador-promocions/{id}")
    public ResponseEntity<TabuladorPromocionDTO> updateTabuladorPromocion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TabuladorPromocionDTO tabuladorPromocionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TabuladorPromocion : {}, {}", id, tabuladorPromocionDTO);
        if (tabuladorPromocionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabuladorPromocionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabuladorPromocionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TabuladorPromocionDTO result = tabuladorPromocionService.update(tabuladorPromocionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabuladorPromocionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tabulador-promocions/:id} : Partial updates given fields of an existing tabuladorPromocion, field will ignore if it is null
     *
     * @param id the id of the tabuladorPromocionDTO to save.
     * @param tabuladorPromocionDTO the tabuladorPromocionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabuladorPromocionDTO,
     * or with status {@code 400 (Bad Request)} if the tabuladorPromocionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tabuladorPromocionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tabuladorPromocionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tabulador-promocions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TabuladorPromocionDTO> partialUpdateTabuladorPromocion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TabuladorPromocionDTO tabuladorPromocionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TabuladorPromocion partially : {}, {}", id, tabuladorPromocionDTO);
        if (tabuladorPromocionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabuladorPromocionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabuladorPromocionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TabuladorPromocionDTO> result = tabuladorPromocionService.partialUpdate(tabuladorPromocionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabuladorPromocionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tabulador-promocions} : get all the tabuladorPromocions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tabuladorPromocions in body.
     */
    @GetMapping("/tabulador-promocions")
    public List<TabuladorPromocionDTO> getAllTabuladorPromocions() {
        log.debug("REST request to get all TabuladorPromocions");
        return tabuladorPromocionService.findAll();
    }

    /**
     * {@code GET  /tabulador-promocions/:id} : get the "id" tabuladorPromocion.
     *
     * @param id the id of the tabuladorPromocionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tabuladorPromocionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tabulador-promocions/{id}")
    public ResponseEntity<TabuladorPromocionDTO> getTabuladorPromocion(@PathVariable Long id) {
        log.debug("REST request to get TabuladorPromocion : {}", id);
        Optional<TabuladorPromocionDTO> tabuladorPromocionDTO = tabuladorPromocionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tabuladorPromocionDTO);
    }

    /**
     * {@code DELETE  /tabulador-promocions/:id} : delete the "id" tabuladorPromocion.
     *
     * @param id the id of the tabuladorPromocionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tabulador-promocions/{id}")
    public ResponseEntity<Void> deleteTabuladorPromocion(@PathVariable Long id) {
        log.debug("REST request to delete TabuladorPromocion : {}", id);
        tabuladorPromocionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
