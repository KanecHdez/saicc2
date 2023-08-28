package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.TabuladorPromocion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TabuladorPromocionDTO implements Serializable {

    private Long id;

    private LocalDate inicioVigencia;

    private LocalDate finVigencia;

    @Size(max = 50)
    private String descripcion;

    private Boolean activo;

    @Size(max = 100)
    private String modifiedBy;

    private Instant createdDate;

    private Instant lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(LocalDate finVigencia) {
        this.finVigencia = finVigencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabuladorPromocionDTO)) {
            return false;
        }

        TabuladorPromocionDTO tabuladorPromocionDTO = (TabuladorPromocionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tabuladorPromocionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabuladorPromocionDTO{" +
            "id=" + getId() +
            ", inicioVigencia='" + getInicioVigencia() + "'" +
            ", finVigencia='" + getFinVigencia() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", activo='" + getActivo() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
