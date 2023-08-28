package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.TabuladorPromocion;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TabuladorPromocion} and its DTO {@link TabuladorPromocionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TabuladorPromocionMapper extends EntityMapper<TabuladorPromocionDTO, TabuladorPromocion> {}
