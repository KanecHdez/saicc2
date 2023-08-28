import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../centro-docente.test-samples';

import { CentroDocenteFormService } from './centro-docente-form.service';

describe('CentroDocente Form Service', () => {
  let service: CentroDocenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentroDocenteFormService);
  });

  describe('Service methods', () => {
    describe('createCentroDocenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentroDocenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cve: expect.any(Object),
            nombre: expect.any(Object),
            comisionDictaminadora: expect.any(Object),
          })
        );
      });

      it('passing ICentroDocente should create a new form with FormGroup', () => {
        const formGroup = service.createCentroDocenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cve: expect.any(Object),
            nombre: expect.any(Object),
            comisionDictaminadora: expect.any(Object),
          })
        );
      });
    });

    describe('getCentroDocente', () => {
      it('should return NewCentroDocente for default CentroDocente initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCentroDocenteFormGroup(sampleWithNewData);

        const centroDocente = service.getCentroDocente(formGroup) as any;

        expect(centroDocente).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentroDocente for empty CentroDocente initial value', () => {
        const formGroup = service.createCentroDocenteFormGroup();

        const centroDocente = service.getCentroDocente(formGroup) as any;

        expect(centroDocente).toMatchObject({});
      });

      it('should return ICentroDocente', () => {
        const formGroup = service.createCentroDocenteFormGroup(sampleWithRequiredData);

        const centroDocente = service.getCentroDocente(formGroup) as any;

        expect(centroDocente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentroDocente should not enable id FormControl', () => {
        const formGroup = service.createCentroDocenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentroDocente should disable id FormControl', () => {
        const formGroup = service.createCentroDocenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
