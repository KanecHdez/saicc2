import dayjs from 'dayjs/esm';
import { IAcademico } from 'app/entities/academico/academico.model';
import { IPuesto } from 'app/entities/puesto/puesto.model';
import { IPeriodo } from 'app/entities/periodo/periodo.model';
import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';
import { ICentroDocente } from 'app/entities/centro-docente/centro-docente.model';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';

export interface IDictamen {
  id: number;
  noDictamen?: number | null;
  fechaPromocion?: dayjs.Dayjs | null;
  puntosAlcanzados?: number | null;
  puntosRequeridos?: number | null;
  puntosExcedentes?: number | null;
  puntosFaltantes?: number | null;
  puntosExcedentesAnterior?: number | null;
  puntosPuestoActual?: number | null;
  puntosPuestoSolicitado?: number | null;
  procede?: boolean | null;
  numeroInstancia?: number | null;
  folioHomologacion?: string | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  academico?: Pick<IAcademico, 'id' | 'nombres'> | null;
  puestoActual?: Pick<IPuesto, 'id' | 'nombre'> | null;
  puestoSolicitado?: Pick<IPuesto, 'id' | 'nombre'> | null;
  periodo?: Pick<IPeriodo, 'id' | 'descripcion'> | null;
  comisionDictaminadora?: Pick<IComisionDictaminadora, 'id' | 'nombre'> | null;
  dependencia?: Pick<ICentroDocente, 'id' | 'nombre'> | null;
  tabuladorPromocion?: Pick<ITabuladorPromocion, 'id'> | null;
}

export type NewDictamen = Omit<IDictamen, 'id'> & { id: null };
