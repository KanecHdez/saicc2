import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dictamen.test-samples';

import { DictamenFormService } from './dictamen-form.service';

describe('Dictamen Form Service', () => {
  let service: DictamenFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DictamenFormService);
  });

  describe('Service methods', () => {
    describe('createDictamenFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDictamenFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            noDictamen: expect.any(Object),
            fechaPromocion: expect.any(Object),
            puntosAlcanzados: expect.any(Object),
            puntosRequeridos: expect.any(Object),
            puntosExcedentes: expect.any(Object),
            puntosFaltantes: expect.any(Object),
            puntosExcedentesAnterior: expect.any(Object),
            puntosPuestoActual: expect.any(Object),
            puntosPuestoSolicitado: expect.any(Object),
            procede: expect.any(Object),
            numeroInstancia: expect.any(Object),
            folioHomologacion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            academico: expect.any(Object),
            puestoActual: expect.any(Object),
            puestoSolicitado: expect.any(Object),
            periodo: expect.any(Object),
            comisionDictaminadora: expect.any(Object),
            dependencia: expect.any(Object),
            tabuladorPromocion: expect.any(Object),
          })
        );
      });

      it('passing IDictamen should create a new form with FormGroup', () => {
        const formGroup = service.createDictamenFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            noDictamen: expect.any(Object),
            fechaPromocion: expect.any(Object),
            puntosAlcanzados: expect.any(Object),
            puntosRequeridos: expect.any(Object),
            puntosExcedentes: expect.any(Object),
            puntosFaltantes: expect.any(Object),
            puntosExcedentesAnterior: expect.any(Object),
            puntosPuestoActual: expect.any(Object),
            puntosPuestoSolicitado: expect.any(Object),
            procede: expect.any(Object),
            numeroInstancia: expect.any(Object),
            folioHomologacion: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
            academico: expect.any(Object),
            puestoActual: expect.any(Object),
            puestoSolicitado: expect.any(Object),
            periodo: expect.any(Object),
            comisionDictaminadora: expect.any(Object),
            dependencia: expect.any(Object),
            tabuladorPromocion: expect.any(Object),
          })
        );
      });
    });

    describe('getDictamen', () => {
      it('should return NewDictamen for default Dictamen initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDictamenFormGroup(sampleWithNewData);

        const dictamen = service.getDictamen(formGroup) as any;

        expect(dictamen).toMatchObject(sampleWithNewData);
      });

      it('should return NewDictamen for empty Dictamen initial value', () => {
        const formGroup = service.createDictamenFormGroup();

        const dictamen = service.getDictamen(formGroup) as any;

        expect(dictamen).toMatchObject({});
      });

      it('should return IDictamen', () => {
        const formGroup = service.createDictamenFormGroup(sampleWithRequiredData);

        const dictamen = service.getDictamen(formGroup) as any;

        expect(dictamen).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDictamen should not enable id FormControl', () => {
        const formGroup = service.createDictamenFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDictamen should disable id FormControl', () => {
        const formGroup = service.createDictamenFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
