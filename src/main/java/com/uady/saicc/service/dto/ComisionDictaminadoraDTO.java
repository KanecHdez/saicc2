package com.uady.saicc.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.uady.saicc.domain.ComisionDictaminadora} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComisionDictaminadoraDTO implements Serializable {

    private Long id;

    @Size(max = 100)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComisionDictaminadoraDTO)) {
            return false;
        }

        ComisionDictaminadoraDTO comisionDictaminadoraDTO = (ComisionDictaminadoraDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comisionDictaminadoraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComisionDictaminadoraDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
