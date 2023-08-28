package com.uady.saicc.web.rest;

import com.uady.saicc.repository.TabuladorActividadProductoRepository;
import com.uady.saicc.service.TabuladorActividadProductoService;
import com.uady.saicc.service.dto.TabuladorActividadProductoDTO;
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
 * REST controller for managing {@link com.uady.saicc.domain.TabuladorActividadProducto}.
 */
@RestController
@RequestMapping("/api")
public class TabuladorActividadProductoResource {

    private final Logger log = LoggerFactory.getLogger(TabuladorActividadProductoResource.class);

    private static final String ENTITY_NAME = "tabuladorActividadProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TabuladorActividadProductoService tabuladorActividadProductoService;

    private final TabuladorActividadProductoRepository tabuladorActividadProductoRepository;

    public TabuladorActividadProductoResource(
        TabuladorActividadProductoService tabuladorActividadProductoService,
        TabuladorActividadProductoRepository tabuladorActividadProductoRepository
    ) {
        this.tabuladorActividadProductoService = tabuladorActividadProductoService;
        this.tabuladorActividadProductoRepository = tabuladorActividadProductoRepository;
    }

    /**
     * {@code POST  /tabulador-actividad-productos} : Create a new tabuladorActividadProducto.
     *
     * @param tabuladorActividadProductoDTO the tabuladorActividadProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tabuladorActividadProductoDTO, or with status {@code 400 (Bad Request)} if the tabuladorActividadProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tabulador-actividad-productos")
    public ResponseEntity<TabuladorActividadProductoDTO> createTabuladorActividadProducto(
        @Valid @RequestBody TabuladorActividadProductoDTO tabuladorActividadProductoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TabuladorActividadProducto : {}", tabuladorActividadProductoDTO);
        if (tabuladorActividadProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tabuladorActividadProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TabuladorActividadProductoDTO result = tabuladorActividadProductoService.save(tabuladorActividadProductoDTO);
        return ResponseEntity
            .created(new URI("/api/tabulador-actividad-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tabulador-actividad-productos/:id} : Updates an existing tabuladorActividadProducto.
     *
     * @param id the id of the tabuladorActividadProductoDTO to save.
     * @param tabuladorActividadProductoDTO the tabuladorActividadProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabuladorActividadProductoDTO,
     * or with status {@code 400 (Bad Request)} if the tabuladorActividadProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tabuladorActividadProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tabulador-actividad-productos/{id}")
    public ResponseEntity<TabuladorActividadProductoDTO> updateTabuladorActividadProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TabuladorActividadProductoDTO tabuladorActividadProductoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TabuladorActividadProducto : {}, {}", id, tabuladorActividadProductoDTO);
        if (tabuladorActividadProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabuladorActividadProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabuladorActividadProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TabuladorActividadProductoDTO result = tabuladorActividadProductoService.update(tabuladorActividadProductoDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabuladorActividadProductoDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /tabulador-actividad-productos/:id} : Partial updates given fields of an existing tabuladorActividadProducto, field will ignore if it is null
     *
     * @param id the id of the tabuladorActividadProductoDTO to save.
     * @param tabuladorActividadProductoDTO the tabuladorActividadProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tabuladorActividadProductoDTO,
     * or with status {@code 400 (Bad Request)} if the tabuladorActividadProductoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tabuladorActividadProductoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tabuladorActividadProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tabulador-actividad-productos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TabuladorActividadProductoDTO> partialUpdateTabuladorActividadProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TabuladorActividadProductoDTO tabuladorActividadProductoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TabuladorActividadProducto partially : {}, {}", id, tabuladorActividadProductoDTO);
        if (tabuladorActividadProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tabuladorActividadProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tabuladorActividadProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TabuladorActividadProductoDTO> result = tabuladorActividadProductoService.partialUpdate(tabuladorActividadProductoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tabuladorActividadProductoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tabulador-actividad-productos} : get all the tabuladorActividadProductos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tabuladorActividadProductos in body.
     */
    @GetMapping("/tabulador-actividad-productos")
    public ResponseEntity<List<TabuladorActividadProductoDTO>> getAllTabuladorActividadProductos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of TabuladorActividadProductos");
        Page<TabuladorActividadProductoDTO> page;
        if (eagerload) {
            page = tabuladorActividadProductoService.findAllWithEagerRelationships(pageable);
        } else {
            page = tabuladorActividadProductoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tabulador-actividad-productos/:id} : get the "id" tabuladorActividadProducto.
     *
     * @param id the id of the tabuladorActividadProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tabuladorActividadProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tabulador-actividad-productos/{id}")
    public ResponseEntity<TabuladorActividadProductoDTO> getTabuladorActividadProducto(@PathVariable Long id) {
        log.debug("REST request to get TabuladorActividadProducto : {}", id);
        Optional<TabuladorActividadProductoDTO> tabuladorActividadProductoDTO = tabuladorActividadProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tabuladorActividadProductoDTO);
    }

    /**
     * {@code DELETE  /tabulador-actividad-productos/:id} : delete the "id" tabuladorActividadProducto.
     *
     * @param id the id of the tabuladorActividadProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tabulador-actividad-productos/{id}")
    public ResponseEntity<Void> deleteTabuladorActividadProducto(@PathVariable Long id) {
        log.debug("REST request to delete TabuladorActividadProducto : {}", id);
        tabuladorActividadProductoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
