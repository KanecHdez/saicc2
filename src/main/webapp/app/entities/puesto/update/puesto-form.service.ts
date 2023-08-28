import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPuesto, NewPuesto } from '../puesto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPuesto for edit and NewPuestoFormGroupInput for create.
 */
type PuestoFormGroupInput = IPuesto | PartialWithRequiredKeyOf<NewPuesto>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPuesto | NewPuesto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type PuestoFormRawValue = FormValueOf<IPuesto>;

type NewPuestoFormRawValue = FormValueOf<NewPuesto>;

type PuestoFormDefaults = Pick<NewPuesto, 'id' | 'createdDate' | 'lastModifiedDate'>;

type PuestoFormGroupContent = {
  id: FormControl<PuestoFormRawValue['id'] | NewPuesto['id']>;
  cve: FormControl<PuestoFormRawValue['cve']>;
  nombre: FormControl<PuestoFormRawValue['nombre']>;
  puntaje: FormControl<PuestoFormRawValue['puntaje']>;
  ranking: FormControl<PuestoFormRawValue['ranking']>;
  modifiedBy: FormControl<PuestoFormRawValue['modifiedBy']>;
  createdDate: FormControl<PuestoFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<PuestoFormRawValue['lastModifiedDate']>;
};

export type PuestoFormGroup = FormGroup<PuestoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PuestoFormService {
  createPuestoFormGroup(puesto: PuestoFormGroupInput = { id: null }): PuestoFormGroup {
    const puestoRawValue = this.convertPuestoToPuestoRawValue({
      ...this.getFormDefaults(),
      ...puesto,
    });
    return new FormGroup<PuestoFormGroupContent>({
      id: new FormControl(
        { value: puestoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cve: new FormControl(puestoRawValue.cve),
      nombre: new FormControl(puestoRawValue.nombre, {
        validators: [Validators.maxLength(100)],
      }),
      puntaje: new FormControl(puestoRawValue.puntaje),
      ranking: new FormControl(puestoRawValue.ranking),
      modifiedBy: new FormControl(puestoRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(puestoRawValue.createdDate),
      lastModifiedDate: new FormControl(puestoRawValue.lastModifiedDate),
    });
  }

  getPuesto(form: PuestoFormGroup): IPuesto | NewPuesto {
    return this.convertPuestoRawValueToPuesto(form.getRawValue() as PuestoFormRawValue | NewPuestoFormRawValue);
  }

  resetForm(form: PuestoFormGroup, puesto: PuestoFormGroupInput): void {
    const puestoRawValue = this.convertPuestoToPuestoRawValue({ ...this.getFormDefaults(), ...puesto });
    form.reset(
      {
        ...puestoRawValue,
        id: { value: puestoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PuestoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertPuestoRawValueToPuesto(rawPuesto: PuestoFormRawValue | NewPuestoFormRawValue): IPuesto | NewPuesto {
    return {
      ...rawPuesto,
      createdDate: dayjs(rawPuesto.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawPuesto.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertPuestoToPuestoRawValue(
    puesto: IPuesto | (Partial<NewPuesto> & PuestoFormDefaults)
  ): PuestoFormRawValue | PartialWithRequiredKeyOf<NewPuestoFormRawValue> {
    return {
      ...puesto,
      createdDate: puesto.createdDate ? puesto.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: puesto.lastModifiedDate ? puesto.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
