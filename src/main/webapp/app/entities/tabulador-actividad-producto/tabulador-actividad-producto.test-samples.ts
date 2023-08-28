import dayjs from 'dayjs/esm';

import { ITabuladorActividadProducto, NewTabuladorActividadProducto } from './tabulador-actividad-producto.model';

export const sampleWithRequiredData: ITabuladorActividadProducto = {
  id: 26186,
};

export const sampleWithPartialData: ITabuladorActividadProducto = {
  id: 65958,
  clave: 'Rojo',
  cveTabProm: 94132,
  nivel: 93439,
  puntosMaximos: 63029,
  createdDate: dayjs('2023-08-24T18:08'),
  lastModifiedDate: dayjs('2023-08-25T15:47'),
};

export const sampleWithFullData: ITabuladorActividadProducto = {
  id: 80782,
  clave: 'SDD Música',
  cveTabProm: 45888,
  nivel: 86994,
  descripcion: 'Bebes Deportes',
  ingresoMinimo: 98849,
  ingresoMaximo: 7988,
  puntosMaximos: 10998,
  modifiedBy: 'Deportes',
  createdDate: dayjs('2023-08-25T04:17'),
  lastModifiedDate: dayjs('2023-08-25T06:03'),
};

export const sampleWithNewData: NewTabuladorActividadProducto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
