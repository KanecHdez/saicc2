import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAcademico, NewAcademico } from '../academico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAcademico for edit and NewAcademicoFormGroupInput for create.
 */
type AcademicoFormGroupInput = IAcademico | PartialWithRequiredKeyOf<NewAcademico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAcademico | NewAcademico> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type AcademicoFormRawValue = FormValueOf<IAcademico>;

type NewAcademicoFormRawValue = FormValueOf<NewAcademico>;

type AcademicoFormDefaults = Pick<NewAcademico, 'id' | 'createdDate' | 'lastModifiedDate'>;

type AcademicoFormGroupContent = {
  id: FormControl<AcademicoFormRawValue['id'] | NewAcademico['id']>;
  cveEmpleado: FormControl<AcademicoFormRawValue['cveEmpleado']>;
  nombres: FormControl<AcademicoFormRawValue['nombres']>;
  primerApellido: FormControl<AcademicoFormRawValue['primerApellido']>;
  segundoApellido: FormControl<AcademicoFormRawValue['segundoApellido']>;
  modifiedBy: FormControl<AcademicoFormRawValue['modifiedBy']>;
  createdDate: FormControl<AcademicoFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<AcademicoFormRawValue['lastModifiedDate']>;
};

export type AcademicoFormGroup = FormGroup<AcademicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AcademicoFormService {
  createAcademicoFormGroup(academico: AcademicoFormGroupInput = { id: null }): AcademicoFormGroup {
    const academicoRawValue = this.convertAcademicoToAcademicoRawValue({
      ...this.getFormDefaults(),
      ...academico,
    });
    return new FormGroup<AcademicoFormGroupContent>({
      id: new FormControl(
        { value: academicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cveEmpleado: new FormControl(academicoRawValue.cveEmpleado),
      nombres: new FormControl(academicoRawValue.nombres, {
        validators: [Validators.maxLength(100)],
      }),
      primerApellido: new FormControl(academicoRawValue.primerApellido, {
        validators: [Validators.maxLength(100)],
      }),
      segundoApellido: new FormControl(academicoRawValue.segundoApellido, {
        validators: [Validators.maxLength(100)],
      }),
      modifiedBy: new FormControl(academicoRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(academicoRawValue.createdDate),
      lastModifiedDate: new FormControl(academicoRawValue.lastModifiedDate),
    });
  }

  getAcademico(form: AcademicoFormGroup): IAcademico | NewAcademico {
    return this.convertAcademicoRawValueToAcademico(form.getRawValue() as AcademicoFormRawValue | NewAcademicoFormRawValue);
  }

  resetForm(form: AcademicoFormGroup, academico: AcademicoFormGroupInput): void {
    const academicoRawValue = this.convertAcademicoToAcademicoRawValue({ ...this.getFormDefaults(), ...academico });
    form.reset(
      {
        ...academicoRawValue,
        id: { value: academicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AcademicoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertAcademicoRawValueToAcademico(rawAcademico: AcademicoFormRawValue | NewAcademicoFormRawValue): IAcademico | NewAcademico {
    return {
      ...rawAcademico,
      createdDate: dayjs(rawAcademico.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawAcademico.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertAcademicoToAcademicoRawValue(
    academico: IAcademico | (Partial<NewAcademico> & AcademicoFormDefaults)
  ): AcademicoFormRawValue | PartialWithRequiredKeyOf<NewAcademicoFormRawValue> {
    return {
      ...academico,
      createdDate: academico.createdDate ? academico.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: academico.lastModifiedDate ? academico.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
