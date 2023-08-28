import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../actividad-producto.test-samples';

import { ActividadProductoFormService } from './actividad-producto-form.service';

describe('ActividadProducto Form Service', () => {
  let service: ActividadProductoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActividadProductoFormService);
  });

  describe('Service methods', () => {
    describe('createActividadProductoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createActividadProductoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            tabuladorActProd: expect.any(Object),
            dictamen: expect.any(Object),
          })
        );
      });

      it('passing IActividadProducto should create a new form with FormGroup', () => {
        const formGroup = service.createActividadProductoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            tabuladorActProd: expect.any(Object),
            dictamen: expect.any(Object),
          })
        );
      });
    });

    describe('getActividadProducto', () => {
      it('should return NewActividadProducto for default ActividadProducto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createActividadProductoFormGroup(sampleWithNewData);

        const actividadProducto = service.getActividadProducto(formGroup) as any;

        expect(actividadProducto).toMatchObject(sampleWithNewData);
      });

      it('should return NewActividadProducto for empty ActividadProducto initial value', () => {
        const formGroup = service.createActividadProductoFormGroup();

        const actividadProducto = service.getActividadProducto(formGroup) as any;

        expect(actividadProducto).toMatchObject({});
      });

      it('should return IActividadProducto', () => {
        const formGroup = service.createActividadProductoFormGroup(sampleWithRequiredData);

        const actividadProducto = service.getActividadProducto(formGroup) as any;

        expect(actividadProducto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IActividadProducto should not enable id FormControl', () => {
        const formGroup = service.createActividadProductoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewActividadProducto should disable id FormControl', () => {
        const formGroup = service.createActividadProductoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
