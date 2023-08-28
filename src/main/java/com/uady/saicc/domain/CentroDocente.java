package com.uady.saicc.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CentroDocente.
 */
@Entity
@Table(name = "centrodocente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroDocente implements Serializable {

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

    @ManyToOne
    private ComisionDictaminadora comisionDictaminadora;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CentroDocente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCve() {
        return this.cve;
    }

    public CentroDocente cve(Integer cve) {
        this.setCve(cve);
        return this;
    }

    public void setCve(Integer cve) {
        this.cve = cve;
    }

    public String getNombre() {
        return this.nombre;
    }

    public CentroDocente nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ComisionDictaminadora getComisionDictaminadora() {
        return this.comisionDictaminadora;
    }

    public void setComisionDictaminadora(ComisionDictaminadora comisionDictaminadora) {
        this.comisionDictaminadora = comisionDictaminadora;
    }

    public CentroDocente comisionDictaminadora(ComisionDictaminadora comisionDictaminadora) {
        this.setComisionDictaminadora(comisionDictaminadora);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroDocente)) {
            return false;
        }
        return id != null && id.equals(((CentroDocente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroDocente{" +
            "id=" + getId() +
            ", cve=" + getCve() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
