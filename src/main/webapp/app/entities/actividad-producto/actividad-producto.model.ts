import dayjs from 'dayjs/esm';
import { ITabuladorActividadProducto } from 'app/entities/tabulador-actividad-producto/tabulador-actividad-producto.model';
import { IDictamen } from 'app/entities/dictamen/dictamen.model';

export interface IActividadProducto {
  id: number;
  descripcion?: string | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  tabuladorActProd?: Pick<ITabuladorActividadProducto, 'id' | 'descripcion'> | null;
  dictamen?: Pick<IDictamen, 'id' | 'noDictamen'> | null;
}

export type NewActividadProducto = Omit<IActividadProducto, 'id'> & { id: null };
