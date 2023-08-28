import dayjs from 'dayjs/esm';

export interface IPuesto {
  id: number;
  cve?: number | null;
  nombre?: string | null;
  puntaje?: number | null;
  ranking?: number | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewPuesto = Omit<IPuesto, 'id'> & { id: null };
