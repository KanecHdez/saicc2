package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.TabuladorActividadProducto;
import com.uady.saicc.domain.TabuladorPromocion;
import com.uady.saicc.service.dto.TabuladorActividadProductoDTO;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TabuladorActividadProducto} and its DTO {@link TabuladorActividadProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TabuladorActividadProductoMapper extends EntityMapper<TabuladorActividadProductoDTO, TabuladorActividadProducto> {
    @Mapping(target = "tabuladorActSuperior", source = "tabuladorActSuperior", qualifiedByName = "tabuladorActividadProductoDescripcion")
    @Mapping(target = "tabulador", source = "tabulador", qualifiedByName = "tabuladorPromocionId")
    TabuladorActividadProductoDTO toDto(TabuladorActividadProducto s);

    @Named("tabuladorActividadProductoDescripcion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    TabuladorActividadProductoDTO toDtoTabuladorActividadProductoDescripcion(TabuladorActividadProducto tabuladorActividadProducto);

    @Named("tabuladorPromocionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TabuladorPromocionDTO toDtoTabuladorPromocionId(TabuladorPromocion tabuladorPromocion);
}
