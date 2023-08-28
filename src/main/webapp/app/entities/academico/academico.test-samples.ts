import dayjs from 'dayjs/esm';

import { IAcademico, NewAcademico } from './academico.model';

export const sampleWithRequiredData: IAcademico = {
  id: 61124,
};

export const sampleWithPartialData: IAcademico = {
  id: 53033,
  cveEmpleado: 87036,
  primerApellido: 'Refinado primary Joyería',
  segundoApellido: 'Interno',
};

export const sampleWithFullData: IAcademico = {
  id: 56425,
  cveEmpleado: 62739,
  nombres: 'Algodón Coche auxiliary',
  primerApellido: 'Innovador Operaciones',
  segundoApellido: 'front-end',
  modifiedBy: 'deploy',
  createdDate: dayjs('2023-08-25T11:27'),
  lastModifiedDate: dayjs('2023-08-25T16:37'),
};

export const sampleWithNewData: NewAcademico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
