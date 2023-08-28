package com.uady.saicc.service.mapper;

import com.uady.saicc.domain.Academico;
import com.uady.saicc.domain.CentroDocente;
import com.uady.saicc.domain.ComisionDictaminadora;
import com.uady.saicc.domain.Dictamen;
import com.uady.saicc.domain.Periodo;
import com.uady.saicc.domain.Puesto;
import com.uady.saicc.domain.TabuladorPromocion;
import com.uady.saicc.service.dto.AcademicoDTO;
import com.uady.saicc.service.dto.CentroDocenteDTO;
import com.uady.saicc.service.dto.ComisionDictaminadoraDTO;
import com.uady.saicc.service.dto.DictamenDTO;
import com.uady.saicc.service.dto.PeriodoDTO;
import com.uady.saicc.service.dto.PuestoDTO;
import com.uady.saicc.service.dto.TabuladorPromocionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dictamen} and its DTO {@link DictamenDTO}.
 */
@Mapper(componentModel = "spring")
public interface DictamenMapper extends EntityMapper<DictamenDTO, Dictamen> {
    @Mapping(target = "academico", source = "academico", qualifiedByName = "academicoNombres")
    @Mapping(target = "puestoActual", source = "puestoActual", qualifiedByName = "puestoNombre")
    @Mapping(target = "puestoSolicitado", source = "puestoSolicitado", qualifiedByName = "puestoNombre")
    @Mapping(target = "periodo", source = "periodo", qualifiedByName = "periodoDescripcion")
    @Mapping(target = "comisionDictaminadora", source = "comisionDictaminadora", qualifiedByName = "comisionDictaminadoraNombre")
    @Mapping(target = "dependencia", source = "dependencia", qualifiedByName = "centroDocenteNombre")
    @Mapping(target = "tabuladorPromocion", source = "tabuladorPromocion", qualifiedByName = "tabuladorPromocionId")
    DictamenDTO toDto(Dictamen s);

    @Named("academicoNombres")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombres", source = "nombres")
    AcademicoDTO toDtoAcademicoNombres(Academico academico);

    @Named("puestoNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    PuestoDTO toDtoPuestoNombre(Puesto puesto);

    @Named("periodoDescripcion")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descripcion", source = "descripcion")
    PeriodoDTO toDtoPeriodoDescripcion(Periodo periodo);

    @Named("comisionDictaminadoraNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ComisionDictaminadoraDTO toDtoComisionDictaminadoraNombre(ComisionDictaminadora comisionDictaminadora);

    @Named("centroDocenteNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    CentroDocenteDTO toDtoCentroDocenteNombre(CentroDocente centroDocente);

    @Named("tabuladorPromocionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TabuladorPromocionDTO toDtoTabuladorPromocionId(TabuladorPromocion tabuladorPromocion);
}
