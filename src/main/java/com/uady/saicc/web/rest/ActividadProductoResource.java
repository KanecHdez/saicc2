package com.uady.saicc.web.rest;

import com.uady.saicc.repository.ActividadProductoRepository;
import com.uady.saicc.service.ActividadProductoService;
import com.uady.saicc.service.dto.ActividadProductoDTO;
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
 * REST controller for managing {@link com.uady.saicc.domain.ActividadProducto}.
 */
@RestController
@RequestMapping("/api")
public class ActividadProductoResource {

    private final Logger log = LoggerFactory.getLogger(ActividadProductoResource.class);

    private static final String ENTITY_NAME = "actividadProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActividadProductoService actividadProductoService;

    private final ActividadProductoRepository actividadProductoRepository;

    public ActividadProductoResource(
        ActividadProductoService actividadProductoService,
        ActividadProductoRepository actividadProductoRepository
    ) {
        this.actividadProductoService = actividadProductoService;
        this.actividadProductoRepository = actividadProductoRepository;
    }

    /**
     * {@code POST  /actividad-productos} : Create a new actividadProducto.
     *
     * @param actividadProductoDTO the actividadProductoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actividadProductoDTO, or with status {@code 400 (Bad Request)} if the actividadProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/actividad-productos")
    public ResponseEntity<ActividadProductoDTO> createActividadProducto(@Valid @RequestBody ActividadProductoDTO actividadProductoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ActividadProducto : {}", actividadProductoDTO);
        if (actividadProductoDTO.getId() != null) {
            throw new BadRequestAlertException("A new actividadProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActividadProductoDTO result = actividadProductoService.save(actividadProductoDTO);
        return ResponseEntity
            .created(new URI("/api/actividad-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /actividad-productos/:id} : Updates an existing actividadProducto.
     *
     * @param id the id of the actividadProductoDTO to save.
     * @param actividadProductoDTO the actividadProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actividadProductoDTO,
     * or with status {@code 400 (Bad Request)} if the actividadProductoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actividadProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/actividad-productos/{id}")
    public ResponseEntity<ActividadProductoDTO> updateActividadProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActividadProductoDTO actividadProductoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ActividadProducto : {}, {}", id, actividadProductoDTO);
        if (actividadProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actividadProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actividadProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ActividadProductoDTO result = actividadProductoService.update(actividadProductoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actividadProductoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /actividad-productos/:id} : Partial updates given fields of an existing actividadProducto, field will ignore if it is null
     *
     * @param id the id of the actividadProductoDTO to save.
     * @param actividadProductoDTO the actividadProductoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actividadProductoDTO,
     * or with status {@code 400 (Bad Request)} if the actividadProductoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the actividadProductoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the actividadProductoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/actividad-productos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActividadProductoDTO> partialUpdateActividadProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActividadProductoDTO actividadProductoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ActividadProducto partially : {}, {}", id, actividadProductoDTO);
        if (actividadProductoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, actividadProductoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!actividadProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActividadProductoDTO> result = actividadProductoService.partialUpdate(actividadProductoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actividadProductoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /actividad-productos} : get all the actividadProductos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actividadProductos in body.
     */
    @GetMapping("/actividad-productos")
    public ResponseEntity<List<ActividadProductoDTO>> getAllActividadProductos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ActividadProductos");
        Page<ActividadProductoDTO> page;
        if (eagerload) {
            page = actividadProductoService.findAllWithEagerRelationships(pageable);
        } else {
            page = actividadProductoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /actividad-productos/:id} : get the "id" actividadProducto.
     *
     * @param id the id of the actividadProductoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actividadProductoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/actividad-productos/{id}")
    public ResponseEntity<ActividadProductoDTO> getActividadProducto(@PathVariable Long id) {
        log.debug("REST request to get ActividadProducto : {}", id);
        Optional<ActividadProductoDTO> actividadProductoDTO = actividadProductoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actividadProductoDTO);
    }

    /**
     * {@code DELETE  /actividad-productos/:id} : delete the "id" actividadProducto.
     *
     * @param id the id of the actividadProductoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/actividad-productos/{id}")
    public ResponseEntity<Void> deleteActividadProducto(@PathVariable Long id) {
        log.debug("REST request to delete ActividadProducto : {}", id);
        actividadProductoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
