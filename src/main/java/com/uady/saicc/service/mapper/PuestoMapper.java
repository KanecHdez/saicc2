package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.Puesto;
import com.uady.saicc.service.dto.PuestoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Puesto} and its DTO {@link PuestoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PuestoMapper extends EntityMapper<PuestoDTO, Puesto> {}
