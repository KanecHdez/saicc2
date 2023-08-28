package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.ComisionDictaminadora;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComisionDictaminadora} and its DTO {@link ComisionDictaminadoraDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComisionDictaminadoraMapper extends EntityMapper<ComisionDictaminadoraDTO, ComisionDictaminadora> {}
