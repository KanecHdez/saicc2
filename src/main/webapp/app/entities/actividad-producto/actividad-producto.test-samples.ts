import dayjs from 'dayjs/esm';

import { IActividadProducto, NewActividadProducto } from './actividad-producto.model';

export const sampleWithRequiredData: IActividadProducto = {
  id: 65028,
};

export const sampleWithPartialData: IActividadProducto = {
  id: 4145,
  lastModifiedDate: dayjs('2023-08-24T19:35'),
};

export const sampleWithFullData: IActividadProducto = {
  id: 36320,
  descripcion: 'microchip distributed Guadalupe',
  modifiedBy: 'SMTP viral hack',
  createdDate: dayjs('2023-08-25T03:09'),
  lastModifiedDate: dayjs('2023-08-25T13:27'),
};

export const sampleWithNewData: NewActividadProducto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
