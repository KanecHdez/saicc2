import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../puesto.test-samples';

import { PuestoFormService } from './puesto-form.service';

describe('Puesto Form Service', () => {
  let service: PuestoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PuestoFormService);
  });

  describe('Service methods', () => {
    describe('createPuestoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPuestoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cve: expect.any(Object),
            nombre: expect.any(Object),
            puntaje: expect.any(Object),
            ranking: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IPuesto should create a new form with FormGroup', () => {
        const formGroup = service.createPuestoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cve: expect.any(Object),
            nombre: expect.any(Object),
            puntaje: expect.any(Object),
            ranking: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getPuesto', () => {
      it('should return NewPuesto for default Puesto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPuestoFormGroup(sampleWithNewData);

        const puesto = service.getPuesto(formGroup) as any;

        expect(puesto).toMatchObject(sampleWithNewData);
      });

      it('should return NewPuesto for empty Puesto initial value', () => {
        const formGroup = service.createPuestoFormGroup();

        const puesto = service.getPuesto(formGroup) as any;

        expect(puesto).toMatchObject({});
      });

      it('should return IPuesto', () => {
        const formGroup = service.createPuestoFormGroup(sampleWithRequiredData);

        const puesto = service.getPuesto(formGroup) as any;

        expect(puesto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPuesto should not enable id FormControl', () => {
        const formGroup = service.createPuestoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPuesto should disable id FormControl', () => {
        const formGroup = service.createPuestoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
