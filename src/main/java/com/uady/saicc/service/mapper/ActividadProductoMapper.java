package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.ActividadProducto;
import com.uady.saicc.domain.Dictamen;
import com.uady.saicc.domain.TabuladorActividadProducto;
import com.uady.saicc.service.dto.ActividadProductoDTO;
import com.uady.saicc.service.dto.DictamenDTO;
import com.uady.saicc.service.dto.TabuladorActividadProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActividadProducto} and its DTO {@link ActividadProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActividadProductoMapper extends EntityMapper<ActividadProductoDTO, ActividadProducto> {
    @Mapping(target = "tabuladorActProd", source = "tabuladorActProd", qualifiedByName = "tabuladorActividadProductoDescripcion")
    @Mapping(target = "dictamen", source = "dictamen", qualifiedByName = "dictamenNoDictamen")
    ActividadProductoDTO toDto(ActividadProducto s);

    @Named("tabuladorActividadProductoDescripcion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    TabuladorActividadProductoDTO toDtoTabuladorActividadProductoDescripcion(TabuladorActividadProducto tabuladorActividadProducto);

    @Named("dictamenNoDictamen")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "noDictamen", source = "noDictamen")
    DictamenDTO toDtoDictamenNoDictamen(Dictamen dictamen);
}
