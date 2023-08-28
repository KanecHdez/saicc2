import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICentroDocente, NewCentroDocente } from '../centro-docente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentroDocente for edit and NewCentroDocenteFormGroupInput for create.
 */
type CentroDocenteFormGroupInput = ICentroDocente | PartialWithRequiredKeyOf<NewCentroDocente>;

type CentroDocenteFormDefaults = Pick<NewCentroDocente, 'id'>;

type CentroDocenteFormGroupContent = {
  id: FormControl<ICentroDocente['id'] | NewCentroDocente['id']>;
  cve: FormControl<ICentroDocente['cve']>;
  nombre: FormControl<ICentroDocente['nombre']>;
  comisionDictaminadora: FormControl<ICentroDocente['comisionDictaminadora']>;
};

export type CentroDocenteFormGroup = FormGroup<CentroDocenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentroDocenteFormService {
  createCentroDocenteFormGroup(centroDocente: CentroDocenteFormGroupInput = { id: null }): CentroDocenteFormGroup {
    const centroDocenteRawValue = {
      ...this.getFormDefaults(),
      ...centroDocente,
    };
    return new FormGroup<CentroDocenteFormGroupContent>({
      id: new FormControl(
        { value: centroDocenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      cve: new FormControl(centroDocenteRawValue.cve),
      nombre: new FormControl(centroDocenteRawValue.nombre, {
        validators: [Validators.maxLength(100)],
      }),
      comisionDictaminadora: new FormControl(centroDocenteRawValue.comisionDictaminadora),
    });
  }

  getCentroDocente(form: CentroDocenteFormGroup): ICentroDocente | NewCentroDocente {
    return form.getRawValue() as ICentroDocente | NewCentroDocente;
  }

  resetForm(form: CentroDocenteFormGroup, centroDocente: CentroDocenteFormGroupInput): void {
    const centroDocenteRawValue = { ...this.getFormDefaults(), ...centroDocente };
    form.reset(
      {
        ...centroDocenteRawValue,
        id: { value: centroDocenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CentroDocenteFormDefaults {
    return {
      id: null,
    };
  }
}
