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
 * A Dictamen.
 */
@Entity
@Table(name = "dictamen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dictamen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "no_dictamen")
    private Integer noDictamen;

    @Column(name = "fecha_promocion")
    private LocalDate fechaPromocion;

    @Column(name = "puntos_alcanzados")
    private Float puntosAlcanzados;

    @Column(name = "puntos_requeridos")
    private Float puntosRequeridos;

    @Column(name = "puntos_excedentes")
    private Float puntosExcedentes;

    @Column(name = "puntos_faltantes")
    private Float puntosFaltantes;

    @Column(name = "puntos_excedentes_anterior")
    private Float puntosExcedentesAnterior;

    @Column(name = "puntos_puesto_actual")
    private Float puntosPuestoActual;

    @Column(name = "puntos_puesto_solicitado")
    private Float puntosPuestoSolicitado;

    @Column(name = "procede")
    private Boolean procede;

    @Column(name = "numero_instancia")
    private Integer numeroInstancia;

    @Size(max = 15)
    @Column(name = "folio_homologacion", length = 15)
    private String folioHomologacion;

    @Size(max = 100)
    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(mappedBy = "dictamen")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tabuladorActProd", "dictamen" }, allowSetters = true)
    private Set<ActividadProducto> actividads = new HashSet<>();

    @ManyToOne
    private Academico academico;

    @ManyToOne
    private Puesto puestoActual;

    @ManyToOne
    private Puesto puestoSolicitado;

    @ManyToOne
    private Periodo periodo;

    @ManyToOne
    private ComisionDictaminadora comisionDictaminadora;

    @ManyToOne
    @JsonIgnoreProperties(value = { "comisionDictaminadora" }, allowSetters = true)
    private CentroDocente dependencia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tabuladorActProds" }, allowSetters = true)
    private TabuladorPromocion tabuladorPromocion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dictamen id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoDictamen() {
        return this.noDictamen;
    }

    public Dictamen noDictamen(Integer noDictamen) {
        this.setNoDictamen(noDictamen);
        return this;
    }

    public void setNoDictamen(Integer noDictamen) {
        this.noDictamen = noDictamen;
    }

    public LocalDate getFechaPromocion() {
        return this.fechaPromocion;
    }

    public Dictamen fechaPromocion(LocalDate fechaPromocion) {
        this.setFechaPromocion(fechaPromocion);
        return this;
    }

    public void setFechaPromocion(LocalDate fechaPromocion) {
        this.fechaPromocion = fechaPromocion;
    }

    public Float getPuntosAlcanzados() {
        return this.puntosAlcanzados;
    }

    public Dictamen puntosAlcanzados(Float puntosAlcanzados) {
        this.setPuntosAlcanzados(puntosAlcanzados);
        return this;
    }

    public void setPuntosAlcanzados(Float puntosAlcanzados) {
        this.puntosAlcanzados = puntosAlcanzados;
    }

    public Float getPuntosRequeridos() {
        return this.puntosRequeridos;
    }

    public Dictamen puntosRequeridos(Float puntosRequeridos) {
        this.setPuntosRequeridos(puntosRequeridos);
        return this;
    }

    public void setPuntosRequeridos(Float puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public Float getPuntosExcedentes() {
        return this.puntosExcedentes;
    }

    public Dictamen puntosExcedentes(Float puntosExcedentes) {
        this.setPuntosExcedentes(puntosExcedentes);
        return this;
    }

    public void setPuntosExcedentes(Float puntosExcedentes) {
        this.puntosExcedentes = puntosExcedentes;
    }

    public Float getPuntosFaltantes() {
        return this.puntosFaltantes;
    }

    public Dictamen puntosFaltantes(Float puntosFaltantes) {
        this.setPuntosFaltantes(puntosFaltantes);
        return this;
    }

    public void setPuntosFaltantes(Float puntosFaltantes) {
        this.puntosFaltantes = puntosFaltantes;
    }

    public Float getPuntosExcedentesAnterior() {
        return this.puntosExcedentesAnterior;
    }

    public Dictamen puntosExcedentesAnterior(Float puntosExcedentesAnterior) {
        this.setPuntosExcedentesAnterior(puntosExcedentesAnterior);
        return this;
    }

    public void setPuntosExcedentesAnterior(Float puntosExcedentesAnterior) {
        this.puntosExcedentesAnterior = puntosExcedentesAnterior;
    }

    public Float getPuntosPuestoActual() {
        return this.puntosPuestoActual;
    }

    public Dictamen puntosPuestoActual(Float puntosPuestoActual) {
        this.setPuntosPuestoActual(puntosPuestoActual);
        return this;
    }

    public void setPuntosPuestoActual(Float puntosPuestoActual) {
        this.puntosPuestoActual = puntosPuestoActual;
    }

    public Float getPuntosPuestoSolicitado() {
        return this.puntosPuestoSolicitado;
    }

    public Dictamen puntosPuestoSolicitado(Float puntosPuestoSolicitado) {
        this.setPuntosPuestoSolicitado(puntosPuestoSolicitado);
        return this;
    }

    public void setPuntosPuestoSolicitado(Float puntosPuestoSolicitado) {
        this.puntosPuestoSolicitado = puntosPuestoSolicitado;
    }

    public Boolean getProcede() {
        return this.procede;
    }

    public Dictamen procede(Boolean procede) {
        this.setProcede(procede);
        return this;
    }

    public void setProcede(Boolean procede) {
        this.procede = procede;
    }

    public Integer getNumeroInstancia() {
        return this.numeroInstancia;
    }

    public Dictamen numeroInstancia(Integer numeroInstancia) {
        this.setNumeroInstancia(numeroInstancia);
        return this;
    }

    public void setNumeroInstancia(Integer numeroInstancia) {
        this.numeroInstancia = numeroInstancia;
    }

    public String getFolioHomologacion() {
        return this.folioHomologacion;
    }

    public Dictamen folioHomologacion(String folioHomologacion) {
        this.setFolioHomologacion(folioHomologacion);
        return this;
    }

    public void setFolioHomologacion(String folioHomologacion) {
        this.folioHomologacion = folioHomologacion;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Dictamen modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Dictamen createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Dictamen lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<ActividadProducto> getActividads() {
        return this.actividads;
    }

    public void setActividads(Set<ActividadProducto> actividadProductos) {
        if (this.actividads != null) {
            this.actividads.forEach(i -> i.setDictamen(null));
        }
        if (actividadProductos != null) {
            actividadProductos.forEach(i -> i.setDictamen(this));
        }
        this.actividads = actividadProductos;
    }

    public Dictamen actividads(Set<ActividadProducto> actividadProductos) {
        this.setActividads(actividadProductos);
        return this;
    }

    public Dictamen addActividad(ActividadProducto actividadProducto) {
        this.actividads.add(actividadProducto);
        actividadProducto.setDictamen(this);
        return this;
    }

    public Dictamen removeActividad(ActividadProducto actividadProducto) {
        this.actividads.remove(actividadProducto);
        actividadProducto.setDictamen(null);
        return this;
    }

    public Academico getAcademico() {
        return this.academico;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    public Dictamen academico(Academico academico) {
        this.setAcademico(academico);
        return this;
    }

    public Puesto getPuestoActual() {
        return this.puestoActual;
    }

    public void setPuestoActual(Puesto puesto) {
        this.puestoActual = puesto;
    }

    public Dictamen puestoActual(Puesto puesto) {
        this.setPuestoActual(puesto);
        return this;
    }

    public Puesto getPuestoSolicitado() {
        return this.puestoSolicitado;
    }

    public void setPuestoSolicitado(Puesto puesto) {
        this.puestoSolicitado = puesto;
    }

    public Dictamen puestoSolicitado(Puesto puesto) {
        this.setPuestoSolicitado(puesto);
        return this;
    }

    public Periodo getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Dictamen periodo(Periodo periodo) {
        this.setPeriodo(periodo);
        return this;
    }

    public ComisionDictaminadora getComisionDictaminadora() {
        return this.comisionDictaminadora;
    }

    public void setComisionDictaminadora(ComisionDictaminadora comisionDictaminadora) {
        this.comisionDictaminadora = comisionDictaminadora;
    }

    public Dictamen comisionDictaminadora(ComisionDictaminadora comisionDictaminadora) {
        this.setComisionDictaminadora(comisionDictaminadora);
        return this;
    }

    public CentroDocente getDependencia() {
        return this.dependencia;
    }

    public void setDependencia(CentroDocente centroDocente) {
        this.dependencia = centroDocente;
    }

    public Dictamen dependencia(CentroDocente centroDocente) {
        this.setDependencia(centroDocente);
        return this;
    }

    public TabuladorPromocion getTabuladorPromocion() {
        return this.tabuladorPromocion;
    }

    public void setTabuladorPromocion(TabuladorPromocion tabuladorPromocion) {
        this.tabuladorPromocion = tabuladorPromocion;
    }

    public Dictamen tabuladorPromocion(TabuladorPromocion tabuladorPromocion) {
        this.setTabuladorPromocion(tabuladorPromocion);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dictamen)) {
            return false;
        }
        return id != null && id.equals(((Dictamen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dictamen{" +
            "id=" + getId() +
            ", noDictamen=" + getNoDictamen() +
            ", fechaPromocion='" + getFechaPromocion() + "'" +
            ", puntosAlcanzados=" + getPuntosAlcanzados() +
            ", puntosRequeridos=" + getPuntosRequeridos() +
            ", puntosExcedentes=" + getPuntosExcedentes() +
            ", puntosFaltantes=" + getPuntosFaltantes() +
            ", puntosExcedentesAnterior=" + getPuntosExcedentesAnterior() +
            ", puntosPuestoActual=" + getPuntosPuestoActual() +
            ", puntosPuestoSolicitado=" + getPuntosPuestoSolicitado() +
            ", procede='" + getProcede() + "'" +
            ", numeroInstancia=" + getNumeroInstancia() +
            ", folioHomologacion='" + getFolioHomologacion() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
