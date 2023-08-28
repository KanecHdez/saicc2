import dayjs from 'dayjs/esm';

import { ITabuladorPromocion, NewTabuladorPromocion } from './tabulador-promocion.model';

export const sampleWithRequiredData: ITabuladorPromocion = {
  id: 25248,
};

export const sampleWithPartialData: ITabuladorPromocion = {
  id: 88674,
  inicioVigencia: dayjs('2023-08-25'),
  descripcion: 'Buckinghamshire Ergonómico Uruguay',
  activo: true,
  modifiedBy: 'grow Juguetería',
  createdDate: dayjs('2023-08-25T09:15'),
  lastModifiedDate: dayjs('2023-08-25T13:42'),
};

export const sampleWithFullData: ITabuladorPromocion = {
  id: 84469,
  inicioVigencia: dayjs('2023-08-25'),
  finVigencia: dayjs('2023-08-25'),
  descripcion: 'calculating Buckinghamshire Investigación',
  activo: false,
  modifiedBy: 'de Cambridgeshire Buckinghamshire',
  createdDate: dayjs('2023-08-25T00:20'),
  lastModifiedDate: dayjs('2023-08-25T15:23'),
};

export const sampleWithNewData: NewTabuladorPromocion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
