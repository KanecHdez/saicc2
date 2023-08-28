import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tabulador-promocion.test-samples';

import { TabuladorPromocionFormService } from './tabulador-promocion-form.service';

describe('TabuladorPromocion Form Service', () => {
  let service: TabuladorPromocionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TabuladorPromocionFormService);
  });

  describe('Service methods', () => {
    describe('createTabuladorPromocionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTabuladorPromocionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inicioVigencia: expect.any(Object),
            finVigencia: expect.any(Object),
            descripcion: expect.any(Object),
            activo: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing ITabuladorPromocion should create a new form with FormGroup', () => {
        const formGroup = service.createTabuladorPromocionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inicioVigencia: expect.any(Object),
            finVigencia: expect.any(Object),
            descripcion: expect.any(Object),
            activo: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getTabuladorPromocion', () => {
      it('should return NewTabuladorPromocion for default TabuladorPromocion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTabuladorPromocionFormGroup(sampleWithNewData);

        const tabuladorPromocion = service.getTabuladorPromocion(formGroup) as any;

        expect(tabuladorPromocion).toMatchObject(sampleWithNewData);
      });

      it('should return NewTabuladorPromocion for empty TabuladorPromocion initial value', () => {
        const formGroup = service.createTabuladorPromocionFormGroup();

        const tabuladorPromocion = service.getTabuladorPromocion(formGroup) as any;

        expect(tabuladorPromocion).toMatchObject({});
      });

      it('should return ITabuladorPromocion', () => {
        const formGroup = service.createTabuladorPromocionFormGroup(sampleWithRequiredData);

        const tabuladorPromocion = service.getTabuladorPromocion(formGroup) as any;

        expect(tabuladorPromocion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITabuladorPromocion should not enable id FormControl', () => {
        const formGroup = service.createTabuladorPromocionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTabuladorPromocion should disable id FormControl', () => {
        const formGroup = service.createTabuladorPromocionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
