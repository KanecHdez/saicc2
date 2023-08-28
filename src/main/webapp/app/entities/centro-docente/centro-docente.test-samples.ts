import { ICentroDocente, NewCentroDocente } from './centro-docente.model';

export const sampleWithRequiredData: ICentroDocente = {
  id: 29410,
};

export const sampleWithPartialData: ICentroDocente = {
  id: 20285,
  cve: 3496,
};

export const sampleWithFullData: ICentroDocente = {
  id: 27552,
  cve: 76582,
  nombre: 'indexing up',
};

export const sampleWithNewData: NewCentroDocente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
