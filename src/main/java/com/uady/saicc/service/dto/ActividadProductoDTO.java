package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.ActividadProducto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActividadProductoDTO implements Serializable {

    private Long id;

    @Size(max = 1000)
    private String descripcion;

    @Size(max = 100)
    private String modifiedBy;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private TabuladorActividadProductoDTO tabuladorActProd;

    private DictamenDTO dictamen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public TabuladorActividadProductoDTO getTabuladorActProd() {
        return tabuladorActProd;
    }

    public void setTabuladorActProd(TabuladorActividadProductoDTO tabuladorActProd) {
        this.tabuladorActProd = tabuladorActProd;
    }

    public DictamenDTO getDictamen() {
        return dictamen;
    }

    public void setDictamen(DictamenDTO dictamen) {
        this.dictamen = dictamen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActividadProductoDTO)) {
            return false;
        }

        ActividadProductoDTO actividadProductoDTO = (ActividadProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, actividadProductoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActividadProductoDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", tabuladorActProd=" + getTabuladorActProd() +
            ", dictamen=" + getDictamen() +
            "}";
    }
}
