import dayjs from 'dayjs/esm';

import { IPeriodo, NewPeriodo } from './periodo.model';

export const sampleWithRequiredData: IPeriodo = {
  id: 50035,
};

export const sampleWithPartialData: IPeriodo = {
  id: 65630,
  periodo: 53005,
  descripcion: 'Investigación Blanco Gris',
  modifiedBy: 'override',
};

export const sampleWithFullData: IPeriodo = {
  id: 17077,
  anio: 97789,
  periodo: 73230,
  descripcion: 'navigate auxiliary',
  modifiedBy: 'magnetic línea distributed',
  createdDate: dayjs('2023-08-25T11:24'),
  lastModifiedDate: dayjs('2023-08-25T14:57'),
};

export const sampleWithNewData: NewPeriodo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
