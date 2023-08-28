package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.Puesto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PuestoDTO implements Serializable {

    private Long id;

    private Integer cve;

    @Size(max = 100)
    private String nombre;

    private Float puntaje;

    private Integer ranking;

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

    public Integer getCve() {
        return cve;
    }

    public void setCve(Integer cve) {
        this.cve = cve;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
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
        if (!(o instanceof PuestoDTO)) {
            return false;
        }

        PuestoDTO puestoDTO = (PuestoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, puestoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PuestoDTO{" +
            "id=" + getId() +
            ", cve=" + getCve() +
            ", nombre='" + getNombre() + "'" +
            ", puntaje=" + getPuntaje() +
            ", ranking=" + getRanking() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
