package com.uady.saicc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TabuladorActividadProducto.
 */
@Entity
@Table(name = "tabuladoractproduc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TabuladorActividadProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "clave", length = 10)
    private String clave;

    @Column(name = "cve_tab_prom")
    private Integer cveTabProm;

    @Column(name = "nivel")
    private Integer nivel;

    @Size(max = 100)
    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "ingreso_minimo")
    private Integer ingresoMinimo;

    @Column(name = "ingreso_maximo")
    private Integer ingresoMaximo;

    @Column(name = "puntos_maximos")
    private Integer puntosMaximos;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tabuladorActSuperior", "tabulador" }, allowSetters = true)
    private TabuladorActividadProducto tabuladorActSuperior;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tabuladorActProds" }, allowSetters = true)
    private TabuladorPromocion tabulador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TabuladorActividadProducto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return this.clave;
    }

    public TabuladorActividadProducto clave(String clave) {
        this.setClave(clave);
        return this;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getCveTabProm() {
        return this.cveTabProm;
    }

    public TabuladorActividadProducto cveTabProm(Integer cveTabProm) {
        this.setCveTabProm(cveTabProm);
        return this;
    }

    public void setCveTabProm(Integer cveTabProm) {
        this.cveTabProm = cveTabProm;
    }

    public Integer getNivel() {
        return this.nivel;
    }

    public TabuladorActividadProducto nivel(Integer nivel) {
        this.setNivel(nivel);
        return this;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TabuladorActividadProducto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIngresoMinimo() {
        return this.ingresoMinimo;
    }

    public TabuladorActividadProducto ingresoMinimo(Integer ingresoMinimo) {
        this.setIngresoMinimo(ingresoMinimo);
        return this;
    }

    public void setIngresoMinimo(Integer ingresoMinimo) {
        this.ingresoMinimo = ingresoMinimo;
    }

    public Integer getIngresoMaximo() {
        return this.ingresoMaximo;
    }

    public TabuladorActividadProducto ingresoMaximo(Integer ingresoMaximo) {
        this.setIngresoMaximo(ingresoMaximo);
        return this;
    }

    public void setIngresoMaximo(Integer ingresoMaximo) {
        this.ingresoMaximo = ingresoMaximo;
    }

    public Integer getPuntosMaximos() {
        return this.puntosMaximos;
    }

    public TabuladorActividadProducto puntosMaximos(Integer puntosMaximos) {
        this.setPuntosMaximos(puntosMaximos);
        return this;
    }

    public void setPuntosMaximos(Integer puntosMaximos) {
        this.puntosMaximos = puntosMaximos;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public TabuladorActividadProducto modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TabuladorActividadProducto createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public TabuladorActividadProducto lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public TabuladorActividadProducto getTabuladorActSuperior() {
        return this.tabuladorActSuperior;
    }

    public void setTabuladorActSuperior(TabuladorActividadProducto tabuladorActividadProducto) {
        this.tabuladorActSuperior = tabuladorActividadProducto;
    }

    public TabuladorActividadProducto tabuladorActSuperior(TabuladorActividadProducto tabuladorActividadProducto) {
        this.setTabuladorActSuperior(tabuladorActividadProducto);
        return this;
    }

    public TabuladorPromocion getTabulador() {
        return this.tabulador;
    }

    public void setTabulador(TabuladorPromocion tabuladorPromocion) {
        this.tabulador = tabuladorPromocion;
    }

    public TabuladorActividadProducto tabulador(TabuladorPromocion tabuladorPromocion) {
        this.setTabulador(tabuladorPromocion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabuladorActividadProducto)) {
            return false;
        }
        return id != null && id.equals(((TabuladorActividadProducto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabuladorActividadProducto{" +
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
            "}";
    }
}
