import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../academico.test-samples';

import { AcademicoFormService } from './academico-form.service';

describe('Academico Form Service', () => {
  let service: AcademicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AcademicoFormService);
  });

  describe('Service methods', () => {
    describe('createAcademicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAcademicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cveEmpleado: expect.any(Object),
            nombres: expect.any(Object),
            primerApellido: expect.any(Object),
            segundoApellido: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });

      it('passing IAcademico should create a new form with FormGroup', () => {
        const formGroup = service.createAcademicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cveEmpleado: expect.any(Object),
            nombres: expect.any(Object),
            primerApellido: expect.any(Object),
            segundoApellido: expect.any(Object),
            modifiedBy: expect.any(Object),
            createdDate: expect.any(Object),
            lastModifiedDate: expect.any(Object),
          })
        );
      });
    });

    describe('getAcademico', () => {
      it('should return NewAcademico for default Academico initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAcademicoFormGroup(sampleWithNewData);

        const academico = service.getAcademico(formGroup) as any;

        expect(academico).toMatchObject(sampleWithNewData);
      });

      it('should return NewAcademico for empty Academico initial value', () => {
        const formGroup = service.createAcademicoFormGroup();

        const academico = service.getAcademico(formGroup) as any;

        expect(academico).toMatchObject({});
      });

      it('should return IAcademico', () => {
        const formGroup = service.createAcademicoFormGroup(sampleWithRequiredData);

        const academico = service.getAcademico(formGroup) as any;

        expect(academico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAcademico should not enable id FormControl', () => {
        const formGroup = service.createAcademicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAcademico should disable id FormControl', () => {
        const formGroup = service.createAcademicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
