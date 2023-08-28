package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.Dictamen} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DictamenDTO implements Serializable {

    private Long id;

    private Integer noDictamen;

    private LocalDate fechaPromocion;

    private Float puntosAlcanzados;

    private Float puntosRequeridos;

    private Float puntosExcedentes;

    private Float puntosFaltantes;

    private Float puntosExcedentesAnterior;

    private Float puntosPuestoActual;

    private Float puntosPuestoSolicitado;

    private Boolean procede;

    private Integer numeroInstancia;

    @Size(max = 15)
    private String folioHomologacion;

    @Size(max = 100)
    private String modifiedBy;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private AcademicoDTO academico;

    private PuestoDTO puestoActual;

    private PuestoDTO puestoSolicitado;

    private PeriodoDTO periodo;

    private ComisionDictaminadoraDTO comisionDictaminadora;

    private CentroDocenteDTO dependencia;

    private TabuladorPromocionDTO tabuladorPromocion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNoDictamen() {
        return noDictamen;
    }

    public void setNoDictamen(Integer noDictamen) {
        this.noDictamen = noDictamen;
    }

    public LocalDate getFechaPromocion() {
        return fechaPromocion;
    }

    public void setFechaPromocion(LocalDate fechaPromocion) {
        this.fechaPromocion = fechaPromocion;
    }

    public Float getPuntosAlcanzados() {
        return puntosAlcanzados;
    }

    public void setPuntosAlcanzados(Float puntosAlcanzados) {
        this.puntosAlcanzados = puntosAlcanzados;
    }

    public Float getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Float puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public Float getPuntosExcedentes() {
        return puntosExcedentes;
    }

    public void setPuntosExcedentes(Float puntosExcedentes) {
        this.puntosExcedentes = puntosExcedentes;
    }

    public Float getPuntosFaltantes() {
        return puntosFaltantes;
    }

    public void setPuntosFaltantes(Float puntosFaltantes) {
        this.puntosFaltantes = puntosFaltantes;
    }

    public Float getPuntosExcedentesAnterior() {
        return puntosExcedentesAnterior;
    }

    public void setPuntosExcedentesAnterior(Float puntosExcedentesAnterior) {
        this.puntosExcedentesAnterior = puntosExcedentesAnterior;
    }

    public Float getPuntosPuestoActual() {
        return puntosPuestoActual;
    }

    public void setPuntosPuestoActual(Float puntosPuestoActual) {
        this.puntosPuestoActual = puntosPuestoActual;
    }

    public Float getPuntosPuestoSolicitado() {
        return puntosPuestoSolicitado;
    }

    public void setPuntosPuestoSolicitado(Float puntosPuestoSolicitado) {
        this.puntosPuestoSolicitado = puntosPuestoSolicitado;
    }

    public Boolean getProcede() {
        return procede;
    }

    public void setProcede(Boolean procede) {
        this.procede = procede;
    }

    public Integer getNumeroInstancia() {
        return numeroInstancia;
    }

    public void setNumeroInstancia(Integer numeroInstancia) {
        this.numeroInstancia = numeroInstancia;
    }

    public String getFolioHomologacion() {
        return folioHomologacion;
    }

    public void setFolioHomologacion(String folioHomologacion) {
        this.folioHomologacion = folioHomologacion;
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

    public AcademicoDTO getAcademico() {
        return academico;
    }

    public void setAcademico(AcademicoDTO academico) {
        this.academico = academico;
    }

    public PuestoDTO getPuestoActual() {
        return puestoActual;
    }

    public void setPuestoActual(PuestoDTO puestoActual) {
        this.puestoActual = puestoActual;
    }

    public PuestoDTO getPuestoSolicitado() {
        return puestoSolicitado;
    }

    public void setPuestoSolicitado(PuestoDTO puestoSolicitado) {
        this.puestoSolicitado = puestoSolicitado;
    }

    public PeriodoDTO getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoDTO periodo) {
        this.periodo = periodo;
    }

    public ComisionDictaminadoraDTO getComisionDictaminadora() {
        return comisionDictaminadora;
    }

    public void setComisionDictaminadora(ComisionDictaminadoraDTO comisionDictaminadora) {
        this.comisionDictaminadora = comisionDictaminadora;
    }

    public CentroDocenteDTO getDependencia() {
        return dependencia;
    }

    public void setDependencia(CentroDocenteDTO dependencia) {
        this.dependencia = dependencia;
    }

    public TabuladorPromocionDTO getTabuladorPromocion() {
        return tabuladorPromocion;
    }

    public void setTabuladorPromocion(TabuladorPromocionDTO tabuladorPromocion) {
        this.tabuladorPromocion = tabuladorPromocion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DictamenDTO)) {
            return false;
        }

        DictamenDTO dictamenDTO = (DictamenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dictamenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DictamenDTO{" +
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
            ", academico=" + getAcademico() +
            ", puestoActual=" + getPuestoActual() +
            ", puestoSolicitado=" + getPuestoSolicitado() +
            ", periodo=" + getPeriodo() +
            ", comisionDictaminadora=" + getComisionDictaminadora() +
            ", dependencia=" + getDependencia() +
            ", tabuladorPromocion=" + getTabuladorPromocion() +
            "}";
    }
}
