import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPeriodo, NewPeriodo } from '../periodo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeriodo for edit and NewPeriodoFormGroupInput for create.
 */
type PeriodoFormGroupInput = IPeriodo | PartialWithRequiredKeyOf<NewPeriodo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPeriodo | NewPeriodo> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type PeriodoFormRawValue = FormValueOf<IPeriodo>;

type NewPeriodoFormRawValue = FormValueOf<NewPeriodo>;

type PeriodoFormDefaults = Pick<NewPeriodo, 'id' | 'createdDate' | 'lastModifiedDate'>;

type PeriodoFormGroupContent = {
  id: FormControl<PeriodoFormRawValue['id'] | NewPeriodo['id']>;
  anio: FormControl<PeriodoFormRawValue['anio']>;
  periodo: FormControl<PeriodoFormRawValue['periodo']>;
  descripcion: FormControl<PeriodoFormRawValue['descripcion']>;
  modifiedBy: FormControl<PeriodoFormRawValue['modifiedBy']>;
  createdDate: FormControl<PeriodoFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<PeriodoFormRawValue['lastModifiedDate']>;
};

export type PeriodoFormGroup = FormGroup<PeriodoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeriodoFormService {
  createPeriodoFormGroup(periodo: PeriodoFormGroupInput = { id: null }): PeriodoFormGroup {
    const periodoRawValue = this.convertPeriodoToPeriodoRawValue({
      ...this.getFormDefaults(),
      ...periodo,
    });
    return new FormGroup<PeriodoFormGroupContent>({
      id: new FormControl(
        { value: periodoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      anio: new FormControl(periodoRawValue.anio),
      periodo: new FormControl(periodoRawValue.periodo),
      descripcion: new FormControl(periodoRawValue.descripcion, {
        validators: [Validators.maxLength(50)],
      }),
      modifiedBy: new FormControl(periodoRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(periodoRawValue.createdDate),
      lastModifiedDate: new FormControl(periodoRawValue.lastModifiedDate),
    });
  }

  getPeriodo(form: PeriodoFormGroup): IPeriodo | NewPeriodo {
    return this.convertPeriodoRawValueToPeriodo(form.getRawValue() as PeriodoFormRawValue | NewPeriodoFormRawValue);
  }

  resetForm(form: PeriodoFormGroup, periodo: PeriodoFormGroupInput): void {
    const periodoRawValue = this.convertPeriodoToPeriodoRawValue({ ...this.getFormDefaults(), ...periodo });
    form.reset(
      {
        ...periodoRawValue,
        id: { value: periodoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PeriodoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertPeriodoRawValueToPeriodo(rawPeriodo: PeriodoFormRawValue | NewPeriodoFormRawValue): IPeriodo | NewPeriodo {
    return {
      ...rawPeriodo,
      createdDate: dayjs(rawPeriodo.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawPeriodo.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertPeriodoToPeriodoRawValue(
    periodo: IPeriodo | (Partial<NewPeriodo> & PeriodoFormDefaults)
  ): PeriodoFormRawValue | PartialWithRequiredKeyOf<NewPeriodoFormRawValue> {
    return {
      ...periodo,
      createdDate: periodo.createdDate ? periodo.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: periodo.lastModifiedDate ? periodo.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
