import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITabuladorActividadProducto, NewTabuladorActividadProducto } from '../tabulador-actividad-producto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITabuladorActividadProducto for edit and NewTabuladorActividadProductoFormGroupInput for create.
 */
type TabuladorActividadProductoFormGroupInput = ITabuladorActividadProducto | PartialWithRequiredKeyOf<NewTabuladorActividadProducto>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITabuladorActividadProducto | NewTabuladorActividadProducto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type TabuladorActividadProductoFormRawValue = FormValueOf<ITabuladorActividadProducto>;

type NewTabuladorActividadProductoFormRawValue = FormValueOf<NewTabuladorActividadProducto>;

type TabuladorActividadProductoFormDefaults = Pick<NewTabuladorActividadProducto, 'id' | 'createdDate' | 'lastModifiedDate'>;

type TabuladorActividadProductoFormGroupContent = {
  id: FormControl<TabuladorActividadProductoFormRawValue['id'] | NewTabuladorActividadProducto['id']>;
  clave: FormControl<TabuladorActividadProductoFormRawValue['clave']>;
  cveTabProm: FormControl<TabuladorActividadProductoFormRawValue['cveTabProm']>;
  nivel: FormControl<TabuladorActividadProductoFormRawValue['nivel']>;
  descripcion: FormControl<TabuladorActividadProductoFormRawValue['descripcion']>;
  ingresoMinimo: FormControl<TabuladorActividadProductoFormRawValue['ingresoMinimo']>;
  ingresoMaximo: FormControl<TabuladorActividadProductoFormRawValue['ingresoMaximo']>;
  puntosMaximos: FormControl<TabuladorActividadProductoFormRawValue['puntosMaximos']>;
  modifiedBy: FormControl<TabuladorActividadProductoFormRawValue['modifiedBy']>;
  createdDate: FormControl<TabuladorActividadProductoFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<TabuladorActividadProductoFormRawValue['lastModifiedDate']>;
  tabuladorActSuperior: FormControl<TabuladorActividadProductoFormRawValue['tabuladorActSuperior']>;
  tabulador: FormControl<TabuladorActividadProductoFormRawValue['tabulador']>;
};

export type TabuladorActividadProductoFormGroup = FormGroup<TabuladorActividadProductoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TabuladorActividadProductoFormService {
  createTabuladorActividadProductoFormGroup(
    tabuladorActividadProducto: TabuladorActividadProductoFormGroupInput = { id: null }
  ): TabuladorActividadProductoFormGroup {
    const tabuladorActividadProductoRawValue = this.convertTabuladorActividadProductoToTabuladorActividadProductoRawValue({
      ...this.getFormDefaults(),
      ...tabuladorActividadProducto,
    });
    return new FormGroup<TabuladorActividadProductoFormGroupContent>({
      id: new FormControl(
        { value: tabuladorActividadProductoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      clave: new FormControl(tabuladorActividadProductoRawValue.clave, {
        validators: [Validators.maxLength(10)],
      }),
      cveTabProm: new FormControl(tabuladorActividadProductoRawValue.cveTabProm),
      nivel: new FormControl(tabuladorActividadProductoRawValue.nivel),
      descripcion: new FormControl(tabuladorActividadProductoRawValue.descripcion, {
        validators: [Validators.maxLength(100)],
      }),
      ingresoMinimo: new FormControl(tabuladorActividadProductoRawValue.ingresoMinimo),
      ingresoMaximo: new FormControl(tabuladorActividadProductoRawValue.ingresoMaximo),
      puntosMaximos: new FormControl(tabuladorActividadProductoRawValue.puntosMaximos),
      modifiedBy: new FormControl(tabuladorActividadProductoRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(tabuladorActividadProductoRawValue.createdDate),
      lastModifiedDate: new FormControl(tabuladorActividadProductoRawValue.lastModifiedDate),
      tabuladorActSuperior: new FormControl(tabuladorActividadProductoRawValue.tabuladorActSuperior),
      tabulador: new FormControl(tabuladorActividadProductoRawValue.tabulador),
    });
  }

  getTabuladorActividadProducto(form: TabuladorActividadProductoFormGroup): ITabuladorActividadProducto | NewTabuladorActividadProducto {
    return this.convertTabuladorActividadProductoRawValueToTabuladorActividadProducto(
      form.getRawValue() as TabuladorActividadProductoFormRawValue | NewTabuladorActividadProductoFormRawValue
    );
  }

  resetForm(form: TabuladorActividadProductoFormGroup, tabuladorActividadProducto: TabuladorActividadProductoFormGroupInput): void {
    const tabuladorActividadProductoRawValue = this.convertTabuladorActividadProductoToTabuladorActividadProductoRawValue({
      ...this.getFormDefaults(),
      ...tabuladorActividadProducto,
    });
    form.reset(
      {
        ...tabuladorActividadProductoRawValue,
        id: { value: tabuladorActividadProductoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TabuladorActividadProductoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertTabuladorActividadProductoRawValueToTabuladorActividadProducto(
    rawTabuladorActividadProducto: TabuladorActividadProductoFormRawValue | NewTabuladorActividadProductoFormRawValue
  ): ITabuladorActividadProducto | NewTabuladorActividadProducto {
    return {
      ...rawTabuladorActividadProducto,
      createdDate: dayjs(rawTabuladorActividadProducto.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawTabuladorActividadProducto.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertTabuladorActividadProductoToTabuladorActividadProductoRawValue(
    tabuladorActividadProducto:
      | ITabuladorActividadProducto
      | (Partial<NewTabuladorActividadProducto> & TabuladorActividadProductoFormDefaults)
  ): TabuladorActividadProductoFormRawValue | PartialWithRequiredKeyOf<NewTabuladorActividadProductoFormRawValue> {
    return {
      ...tabuladorActividadProducto,
      createdDate: tabuladorActividadProducto.createdDate ? tabuladorActividadProducto.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: tabuladorActividadProducto.lastModifiedDate
        ? tabuladorActividadProducto.lastModifiedDate.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
