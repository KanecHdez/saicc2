package com.uady.saicc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TabuladorPromocion.
 */
@Entity
@Table(name = "tabuladorpromocion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TabuladorPromocion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inicio_vigencia")
    private LocalDate inicioVigencia;

    @Column(name = "fin_vigencia")
    private LocalDate finVigencia;

    @Size(max = 50)
    @Column(name = "descripcion", length = 50)
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "tabulador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tabuladorActSuperior", "tabulador" }, allowSetters = true)
    private Set<TabuladorActividadProducto> tabuladorActProds = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TabuladorPromocion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVigencia() {
        return this.inicioVigencia;
    }

    public TabuladorPromocion inicioVigencia(LocalDate inicioVigencia) {
        this.setInicioVigencia(inicioVigencia);
        return this;
    }

    public void setInicioVigencia(LocalDate inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDate getFinVigencia() {
        return this.finVigencia;
    }

    public TabuladorPromocion finVigencia(LocalDate finVigencia) {
        this.setFinVigencia(finVigencia);
        return this;
    }

    public void setFinVigencia(LocalDate finVigencia) {
        this.finVigencia = finVigencia;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public TabuladorPromocion descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public TabuladorPromocion activo(Boolean activo) {
        this.setActivo(activo);
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public TabuladorPromocion modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TabuladorPromocion createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public TabuladorPromocion lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<TabuladorActividadProducto> getTabuladorActProds() {
        return this.tabuladorActProds;
    }

    public void setTabuladorActProds(Set<TabuladorActividadProducto> tabuladorActividadProductos) {
        if (this.tabuladorActProds != null) {
            this.tabuladorActProds.forEach(i -> i.setTabulador(null));
        }
        if (tabuladorActividadProductos != null) {
            tabuladorActividadProductos.forEach(i -> i.setTabulador(this));
        }
        this.tabuladorActProds = tabuladorActividadProductos;
    }

    public TabuladorPromocion tabuladorActProds(Set<TabuladorActividadProducto> tabuladorActividadProductos) {
        this.setTabuladorActProds(tabuladorActividadProductos);
        return this;
    }

    public TabuladorPromocion addTabuladorActProd(TabuladorActividadProducto tabuladorActividadProducto) {
        this.tabuladorActProds.add(tabuladorActividadProducto);
        tabuladorActividadProducto.setTabulador(this);
        return this;
    }

    public TabuladorPromocion removeTabuladorActProd(TabuladorActividadProducto tabuladorActividadProducto) {
        this.tabuladorActProds.remove(tabuladorActividadProducto);
        tabuladorActividadProducto.setTabulador(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabuladorPromocion)) {
            return false;
        }
        return id != null && id.equals(((TabuladorPromocion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TabuladorPromocion{" +
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
