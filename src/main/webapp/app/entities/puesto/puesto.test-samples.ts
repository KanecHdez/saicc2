import dayjs from 'dayjs/esm';

import { IPuesto, NewPuesto } from './puesto.model';

export const sampleWithRequiredData: IPuesto = {
  id: 43974,
};

export const sampleWithPartialData: IPuesto = {
  id: 78737,
  cve: 50764,
  puntaje: 53317,
  ranking: 75775,
  modifiedBy: 'Buckinghamshire',
  createdDate: dayjs('2023-08-25T04:24'),
  lastModifiedDate: dayjs('2023-08-25T03:27'),
};

export const sampleWithFullData: IPuesto = {
  id: 4360,
  cve: 45707,
  nombre: 'Avon navigating Extendido',
  puntaje: 40178,
  ranking: 11688,
  modifiedBy: 'XSS Hogar Salchichas',
  createdDate: dayjs('2023-08-25T17:02'),
  lastModifiedDate: dayjs('2023-08-24T19:14'),
};

export const sampleWithNewData: NewPuesto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
