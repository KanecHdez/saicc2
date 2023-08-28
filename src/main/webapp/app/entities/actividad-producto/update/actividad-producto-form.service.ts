import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IActividadProducto, NewActividadProducto } from '../actividad-producto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IActividadProducto for edit and NewActividadProductoFormGroupInput for create.
 */
type ActividadProductoFormGroupInput = IActividadProducto | PartialWithRequiredKeyOf<NewActividadProducto>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IActividadProducto | NewActividadProducto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type ActividadProductoFormRawValue = FormValueOf<IActividadProducto>;

type NewActividadProductoFormRawValue = FormValueOf<NewActividadProducto>;

type ActividadProductoFormDefaults = Pick<NewActividadProducto, 'id' | 'createdDate' | 'lastModifiedDate'>;

type ActividadProductoFormGroupContent = {
  id: FormControl<ActividadProductoFormRawValue['id'] | NewActividadProducto['id']>;
  descripcion: FormControl<ActividadProductoFormRawValue['descripcion']>;
  modifiedBy: FormControl<ActividadProductoFormRawValue['modifiedBy']>;
  createdDate: FormControl<ActividadProductoFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<ActividadProductoFormRawValue['lastModifiedDate']>;
  tabuladorActProd: FormControl<ActividadProductoFormRawValue['tabuladorActProd']>;
  dictamen: FormControl<ActividadProductoFormRawValue['dictamen']>;
};

export type ActividadProductoFormGroup = FormGroup<ActividadProductoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ActividadProductoFormService {
  createActividadProductoFormGroup(actividadProducto: ActividadProductoFormGroupInput = { id: null }): ActividadProductoFormGroup {
    const actividadProductoRawValue = this.convertActividadProductoToActividadProductoRawValue({
      ...this.getFormDefaults(),
      ...actividadProducto,
    });
    return new FormGroup<ActividadProductoFormGroupContent>({
      id: new FormControl(
        { value: actividadProductoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      descripcion: new FormControl(actividadProductoRawValue.descripcion, {
        validators: [Validators.maxLength(1000)],
      }),
      modifiedBy: new FormControl(actividadProductoRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(actividadProductoRawValue.createdDate),
      lastModifiedDate: new FormControl(actividadProductoRawValue.lastModifiedDate),
      tabuladorActProd: new FormControl(actividadProductoRawValue.tabuladorActProd),
      dictamen: new FormControl(actividadProductoRawValue.dictamen),
    });
  }

  getActividadProducto(form: ActividadProductoFormGroup): IActividadProducto | NewActividadProducto {
    return this.convertActividadProductoRawValueToActividadProducto(
      form.getRawValue() as ActividadProductoFormRawValue | NewActividadProductoFormRawValue
    );
  }

  resetForm(form: ActividadProductoFormGroup, actividadProducto: ActividadProductoFormGroupInput): void {
    const actividadProductoRawValue = this.convertActividadProductoToActividadProductoRawValue({
      ...this.getFormDefaults(),
      ...actividadProducto,
    });
    form.reset(
      {
        ...actividadProductoRawValue,
        id: { value: actividadProductoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ActividadProductoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertActividadProductoRawValueToActividadProducto(
    rawActividadProducto: ActividadProductoFormRawValue | NewActividadProductoFormRawValue
  ): IActividadProducto | NewActividadProducto {
    return {
      ...rawActividadProducto,
      createdDate: dayjs(rawActividadProducto.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawActividadProducto.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertActividadProductoToActividadProductoRawValue(
    actividadProducto: IActividadProducto | (Partial<NewActividadProducto> & ActividadProductoFormDefaults)
  ): ActividadProductoFormRawValue | PartialWithRequiredKeyOf<NewActividadProductoFormRawValue> {
    return {
      ...actividadProducto,
      createdDate: actividadProducto.createdDate ? actividadProducto.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: actividadProducto.lastModifiedDate ? actividadProducto.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
