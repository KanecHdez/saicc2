package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.TabuladorActividadProducto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TabuladorActividadProductoDTO implements Serializable {

    private Long id;

    @Size(max = 10)
    private String clave;

    private Integer cveTabProm;

    private Integer nivel;

    @Size(max = 100)
    private String descripcion;

    private Integer ingresoMinimo;

    private Integer ingresoMaximo;

    private Integer puntosMaximos;

    @Size(max = 100)
    private String modifiedBy;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private TabuladorActividadProductoDTO tabuladorActSuperior;

    private TabuladorPromocionDTO tabulador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getCveTabProm() {
        return cveTabProm;
    }

    public void setCveTabProm(Integer cveTabProm) {
        this.cveTabProm = cveTabProm;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIngresoMinimo() {
        return ingresoMinimo;
    }

    public void setIngresoMinimo(Integer ingresoMinimo) {
        this.ingresoMinimo = ingresoMinimo;
    }

    public Integer getIngresoMaximo() {
        return ingresoMaximo;
    }

    public void setIngresoMaximo(Integer ingresoMaximo) {
        this.ingresoMaximo = ingresoMaximo;
    }

    public Integer getPuntosMaximos() {
        return puntosMaximos;
    }

    public void setPuntosMaximos(Integer puntosMaximos) {
        this.puntosMaximos = puntosMaximos;
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

    public TabuladorActividadProductoDTO getTabuladorActSuperior() {
        return tabuladorActSuperior;
    }

    public void setTabuladorActSuperior(TabuladorActividadProductoDTO tabuladorActSuperior) {
        this.tabuladorActSuperior = tabuladorActSuperior;
    }

    public TabuladorPromocionDTO getTabulador() {
        return tabulador;
    }

    public void setTabulador(TabuladorPromocionDTO tabulador) {
        this.tabulador = tabulador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabuladorActividadProductoDTO)) {
            return false;
        }

        TabuladorActividadProductoDTO tabuladorActividadProductoDTO = (TabuladorActividadProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tabuladorActividadProductoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabuladorActividadProductoDTO{" +
            "id=" + getId() +
            ", clave='" + getClave() + "'" +
            ", cveTabProm=" + getCveTabProm() +
            ", nivel=" + getNivel() +
            ", descripcion='" + getDescripcion() + "'" +
            ", ingresoMinimo=" + getIngresoMinimo() +
            ", ingresoMaximo=" + getIngresoMaximo() +
            ", puntosMaximos=" + getPuntosMaximos() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", tabuladorActSuperior=" + getTabuladorActSuperior() +
            ", tabulador=" + getTabulador() +
            "}";
    }
}
