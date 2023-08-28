import dayjs from 'dayjs/esm';

export interface IPeriodo {
  id: number;
  anio?: number | null;
  periodo?: number | null;
  descripcion?: string | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewPeriodo = Omit<IPeriodo, 'id'> & { id: null };
