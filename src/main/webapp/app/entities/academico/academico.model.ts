import dayjs from 'dayjs/esm';

export interface IAcademico {
  id: number;
  cveEmpleado?: number | null;
  nombres?: string | null;
  primerApellido?: string | null;
  segundoApellido?: string | null;
  modifiedBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  lastModifiedDate?: dayjs.Dayjs | null;
}

export type NewAcademico = Omit<IAcademico, 'id'> & { id: null };
