package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.CentroDocente} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CentroDocenteDTO implements Serializable {

    private Long id;

    private Integer cve;

    @Size(max = 100)
    private String nombre;

    private ComisionDictaminadoraDTO comisionDictaminadora;

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

    public ComisionDictaminadoraDTO getComisionDictaminadora() {
        return comisionDictaminadora;
    }

    public void setComisionDictaminadora(ComisionDictaminadoraDTO comisionDictaminadora) {
        this.comisionDictaminadora = comisionDictaminadora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CentroDocenteDTO)) {
            return false;
        }

        CentroDocenteDTO centroDocenteDTO = (CentroDocenteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, centroDocenteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CentroDocenteDTO{" +
            "id=" + getId() +
            ", cve=" + getCve() +
            ", nombre='" + getNombre() + "'" +
            ", comisionDictaminadora=" + getComisionDictaminadora() +
            "}";
    }
}
