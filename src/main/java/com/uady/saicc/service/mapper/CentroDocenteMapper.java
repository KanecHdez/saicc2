package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.CentroDocente;
import com.uady.saicc.domain.ComisionDictaminadora;
import com.uady.saicc.service.dto.CentroDocenteDTO;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroDocente} and its DTO {@link CentroDocenteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroDocenteMapper extends EntityMapper<CentroDocenteDTO, CentroDocente> {
    @Mapping(target = "comisionDictaminadora", source = "comisionDictaminadora", qualifiedByName = "comisionDictaminadoraNombre")
    CentroDocenteDTO toDto(CentroDocente s);

    @Named("comisionDictaminadoraNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ComisionDictaminadoraDTO toDtoComisionDictaminadoraNombre(ComisionDictaminadora comisionDictaminadora);
}
