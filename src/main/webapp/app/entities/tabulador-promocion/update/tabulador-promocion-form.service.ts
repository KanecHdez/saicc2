import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITabuladorPromocion, NewTabuladorPromocion } from '../tabulador-promocion.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITabuladorPromocion for edit and NewTabuladorPromocionFormGroupInput for create.
 */
type TabuladorPromocionFormGroupInput = ITabuladorPromocion | PartialWithRequiredKeyOf<NewTabuladorPromocion>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITabuladorPromocion | NewTabuladorPromocion> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type TabuladorPromocionFormRawValue = FormValueOf<ITabuladorPromocion>;

type NewTabuladorPromocionFormRawValue = FormValueOf<NewTabuladorPromocion>;

type TabuladorPromocionFormDefaults = Pick<NewTabuladorPromocion, 'id' | 'activo' | 'createdDate' | 'lastModifiedDate'>;

type TabuladorPromocionFormGroupContent = {
  id: FormControl<TabuladorPromocionFormRawValue['id'] | NewTabuladorPromocion['id']>;
  inicioVigencia: FormControl<TabuladorPromocionFormRawValue['inicioVigencia']>;
  finVigencia: FormControl<TabuladorPromocionFormRawValue['finVigencia']>;
  descripcion: FormControl<TabuladorPromocionFormRawValue['descripcion']>;
  activo: FormControl<TabuladorPromocionFormRawValue['activo']>;
  modifiedBy: FormControl<TabuladorPromocionFormRawValue['modifiedBy']>;
  createdDate: FormControl<TabuladorPromocionFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<TabuladorPromocionFormRawValue['lastModifiedDate']>;
};

export type TabuladorPromocionFormGroup = FormGroup<TabuladorPromocionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TabuladorPromocionFormService {
  createTabuladorPromocionFormGroup(tabuladorPromocion: TabuladorPromocionFormGroupInput = { id: null }): TabuladorPromocionFormGroup {
    const tabuladorPromocionRawValue = this.convertTabuladorPromocionToTabuladorPromocionRawValue({
      ...this.getFormDefaults(),
      ...tabuladorPromocion,
    });
    return new FormGroup<TabuladorPromocionFormGroupContent>({
      id: new FormControl(
        { value: tabuladorPromocionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      inicioVigencia: new FormControl(tabuladorPromocionRawValue.inicioVigencia),
      finVigencia: new FormControl(tabuladorPromocionRawValue.finVigencia),
      descripcion: new FormControl(tabuladorPromocionRawValue.descripcion, {
        validators: [Validators.maxLength(50)],
      }),
      activo: new FormControl(tabuladorPromocionRawValue.activo),
      modifiedBy: new FormControl(tabuladorPromocionRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(tabuladorPromocionRawValue.createdDate),
      lastModifiedDate: new FormControl(tabuladorPromocionRawValue.lastModifiedDate),
    });
  }

  getTabuladorPromocion(form: TabuladorPromocionFormGroup): ITabuladorPromocion | NewTabuladorPromocion {
    return this.convertTabuladorPromocionRawValueToTabuladorPromocion(
      form.getRawValue() as TabuladorPromocionFormRawValue | NewTabuladorPromocionFormRawValue
    );
  }

  resetForm(form: TabuladorPromocionFormGroup, tabuladorPromocion: TabuladorPromocionFormGroupInput): void {
    const tabuladorPromocionRawValue = this.convertTabuladorPromocionToTabuladorPromocionRawValue({
      ...this.getFormDefaults(),
      ...tabuladorPromocion,
    });
    form.reset(
      {
        ...tabuladorPromocionRawValue,
        id: { value: tabuladorPromocionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TabuladorPromocionFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      activo: false,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertTabuladorPromocionRawValueToTabuladorPromocion(
    rawTabuladorPromocion: TabuladorPromocionFormRawValue | NewTabuladorPromocionFormRawValue
  ): ITabuladorPromocion | NewTabuladorPromocion {
    return {
      ...rawTabuladorPromocion,
      createdDate: dayjs(rawTabuladorPromocion.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawTabuladorPromocion.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertTabuladorPromocionToTabuladorPromocionRawValue(
    tabuladorPromocion: ITabuladorPromocion | (Partial<NewTabuladorPromocion> & TabuladorPromocionFormDefaults)
  ): TabuladorPromocionFormRawValue | PartialWithRequiredKeyOf<NewTabuladorPromocionFormRawValue> {
    return {
      ...tabuladorPromocion,
      createdDate: tabuladorPromocion.createdDate ? tabuladorPromocion.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: tabuladorPromocion.lastModifiedDate ? tabuladorPromocion.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
