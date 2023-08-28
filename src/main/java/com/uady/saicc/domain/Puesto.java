package com.uady.saicc.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Puesto.
 */
@Entity
@Table(name = "puesto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Puesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cve")
    private Integer cve;

    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "puntaje")
    private Float puntaje;

    @Column(name = "ranking")
    private Integer ranking;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Puesto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCve() {
        return this.cve;
    }

    public Puesto cve(Integer cve) {
        this.setCve(cve);
        return this;
    }

    public void setCve(Integer cve) {
        this.cve = cve;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Puesto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPuntaje() {
        return this.puntaje;
    }

    public Puesto puntaje(Float puntaje) {
        this.setPuntaje(puntaje);
        return this;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public Puesto ranking(Integer ranking) {
        this.setRanking(ranking);
        return this;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Puesto modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Puesto createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Puesto lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Puesto)) {
            return false;
        }
        return id != null && id.equals(((Puesto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Puesto{" +
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
