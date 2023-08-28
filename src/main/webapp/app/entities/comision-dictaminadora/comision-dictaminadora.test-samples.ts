import { IComisionDictaminadora, NewComisionDictaminadora } from './comision-dictaminadora.model';

export const sampleWithRequiredData: IComisionDictaminadora = {
  id: 89303,
};

export const sampleWithPartialData: IComisionDictaminadora = {
  id: 92378,
  nombre: 'Open-source',
};

export const sampleWithFullData: IComisionDictaminadora = {
  id: 73165,
  nombre: '9(E.U.A.-9)',
};

export const sampleWithNewData: NewComisionDictaminadora = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
