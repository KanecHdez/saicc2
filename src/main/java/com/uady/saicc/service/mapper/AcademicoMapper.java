package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.Academico;
import com.uady.saicc.service.dto.AcademicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Academico} and its DTO {@link AcademicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AcademicoMapper extends EntityMapper<AcademicoDTO, Academico> {}
