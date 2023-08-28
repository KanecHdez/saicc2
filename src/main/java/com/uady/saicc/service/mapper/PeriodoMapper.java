package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.Periodo;
import com.uady.saicc.service.dto.PeriodoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Periodo} and its DTO {@link PeriodoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeriodoMapper extends EntityMapper<PeriodoDTO, Periodo> {}
