import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../periodo.test-samples';

import { PeriodoFormService } from './periodo-form.service';

describe('Periodo Form Service', () => {
  let service: PeriodoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeriodoFormService);
  });

  describe('Service methods', () => {
    describe('createPeriodoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPeriodoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anio: expect.any(Object),
            periodo: expect.any(Object),
            descripcion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IPeriodo should create a new form with FormGroup', () => {
        const formGroup = service.createPeriodoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anio: expect.any(Object),
            periodo: expect.any(Object),
            descripcion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getPeriodo', () => {
      it('should return NewPeriodo for default Periodo initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPeriodoFormGroup(sampleWithNewData);

        const periodo = service.getPeriodo(formGroup) as any;

        expect(periodo).toMatchObject(sampleWithNewData);
      });

      it('should return NewPeriodo for empty Periodo initial value', () => {
        const formGroup = service.createPeriodoFormGroup();

        const periodo = service.getPeriodo(formGroup) as any;

        expect(periodo).toMatchObject({});
      });

      it('should return IPeriodo', () => {
        const formGroup = service.createPeriodoFormGroup(sampleWithRequiredData);

        const periodo = service.getPeriodo(formGroup) as any;

        expect(periodo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPeriodo should not enable id FormControl', () => {
        const formGroup = service.createPeriodoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPeriodo should disable id FormControl', () => {
        const formGroup = service.createPeriodoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
