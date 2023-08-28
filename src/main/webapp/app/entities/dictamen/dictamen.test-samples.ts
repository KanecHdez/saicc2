import dayjs from 'dayjs/esm';

import { IDictamen, NewDictamen } from './dictamen.model';

export const sampleWithRequiredData: IDictamen = {
  id: 16505,
};

export const sampleWithPartialData: IDictamen = {
  id: 47752,
  fechaPromocion: dayjs('2023-08-25'),
  puntosRequeridos: 97958,
  puntosPuestoActual: 76588,
  puntosPuestoSolicitado: 50335,
  lastModifiedDate: dayjs('2023-08-25T01:28'),
};

export const sampleWithFullData: IDictamen = {
  id: 24377,
  noDictamen: 39232,
  fechaPromocion: dayjs('2023-08-25'),
  puntosAlcanzados: 37234,
  puntosRequeridos: 43137,
  puntosExcedentes: 65531,
  puntosFaltantes: 52332,
  puntosExcedentesAnterior: 85765,
  puntosPuestoActual: 2374,
  puntosPuestoSolicitado: 95960,
  procede: true,
  numeroInstancia: 67101,
  folioHomologacion: 'Market Amarillo',
  modifiedBy: 'Arquitecto',
  createdDate: dayjs('2023-08-24T21:40'),
  lastModifiedDate: dayjs('2023-08-25T04:58'),
};

export const sampleWithNewData: NewDictamen = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
