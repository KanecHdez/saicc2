import dayjs from 'dayjs/esm';

export interface ITabuladorPromocion {
  id: number;
  inicioVigencia?: dayjs.Dayjs | null;
  finVigencia?: dayjs.Dayjs | null;
  descripcion?: string | null;
  activo?: boolean | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewTabuladorPromocion = Omit<ITabuladorPromocion, 'id'> & { id: null };
