import dayjs from 'dayjs/esm';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';

export interface ITabuladorActividadProducto {
  id: number;
  clave?: string | null;
  cveTabProm?: number | null;
  nivel?: number | null;
  descripcion?: string | null;
  ingresoMinimo?: number | null;
  ingresoMaximo?: number | null;
  puntosMaximos?: number | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
  tabuladorActSuperior?: Pick<ITabuladorActividadProducto, 'id' | 'descripcion'> | null;
  tabulador?: Pick<ITabuladorPromocion, 'id'> | null;
}

export type NewTabuladorActividadProducto = Omit<ITabuladorActividadProducto, 'id'> & { id: null };
