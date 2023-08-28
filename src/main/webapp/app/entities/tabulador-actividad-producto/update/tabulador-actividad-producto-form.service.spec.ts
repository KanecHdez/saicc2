import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tabulador-actividad-producto.test-samples';

import { TabuladorActividadProductoFormService } from './tabulador-actividad-producto-form.service';

describe('TabuladorActividadProducto Form Service', () => {
  let service: TabuladorActividadProductoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TabuladorActividadProductoFormService);
  });

  describe('Service methods', () => {
    describe('createTabuladorActividadProductoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            clave: expect.any(Object),
            cveTabProm: expect.any(Object),
            nivel: expect.any(Object),
            descripcion: expect.any(Object),
            ingresoMinimo: expect.any(Object),
            ingresoMaximo: expect.any(Object),
            puntosMaximos: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            tabuladorActSuperior: expect.any(Object),
            tabulador: expect.any(Object),
          })
        );
      });

      it('passing ITabuladorActividadProducto should create a new form with FormGroup', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            clave: expect.any(Object),
            cveTabProm: expect.any(Object),
            nivel: expect.any(Object),
            descripcion: expect.any(Object),
            ingresoMinimo: expect.any(Object),
            ingresoMaximo: expect.any(Object),
            puntosMaximos: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            tabuladorActSuperior: expect.any(Object),
            tabulador: expect.any(Object),
          })
        );
      });
    });

    describe('getTabuladorActividadProducto', () => {
      it('should return NewTabuladorActividadProducto for default TabuladorActividadProducto initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTabuladorActividadProductoFormGroup(sampleWithNewData);

        const tabuladorActividadProducto = service.getTabuladorActividadProducto(formGroup) as any;

        expect(tabuladorActividadProducto).toMatchObject(sampleWithNewData);
      });

      it('should return NewTabuladorActividadProducto for empty TabuladorActividadProducto initial value', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup();

        const tabuladorActividadProducto = service.getTabuladorActividadProducto(formGroup) as any;

        expect(tabuladorActividadProducto).toMatchObject({});
      });

      it('should return ITabuladorActividadProducto', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup(sampleWithRequiredData);

        const tabuladorActividadProducto = service.getTabuladorActividadProducto(formGroup) as any;

        expect(tabuladorActividadProducto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITabuladorActividadProducto should not enable id FormControl', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTabuladorActividadProducto should disable id FormControl', () => {
        const formGroup = service.createTabuladorActividadProductoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
