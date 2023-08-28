import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comision-dictaminadora.test-samples';

import { ComisionDictaminadoraFormService } from './comision-dictaminadora-form.service';

describe('ComisionDictaminadora Form Service', () => {
  let service: ComisionDictaminadoraFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComisionDictaminadoraFormService);
  });

  describe('Service methods', () => {
    describe('createComisionDictaminadoraFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
          })
        );
      });

      it('passing IComisionDictaminadora should create a new form with FormGroup', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombre: expect.any(Object),
          })
        );
      });
    });

    describe('getComisionDictaminadora', () => {
      it('should return NewComisionDictaminadora for default ComisionDictaminadora initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createComisionDictaminadoraFormGroup(sampleWithNewData);

        const comisionDictaminadora = service.getComisionDictaminadora(formGroup) as any;

        expect(comisionDictaminadora).toMatchObject(sampleWithNewData);
      });

      it('should return NewComisionDictaminadora for empty ComisionDictaminadora initial value', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup();

        const comisionDictaminadora = service.getComisionDictaminadora(formGroup) as any;

        expect(comisionDictaminadora).toMatchObject({});
      });

      it('should return IComisionDictaminadora', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup(sampleWithRequiredData);

        const comisionDictaminadora = service.getComisionDictaminadora(formGroup) as any;

        expect(comisionDictaminadora).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComisionDictaminadora should not enable id FormControl', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComisionDictaminadora should disable id FormControl', () => {
        const formGroup = service.createComisionDictaminadoraFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
