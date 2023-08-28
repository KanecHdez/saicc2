import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IComisionDictaminadora, NewComisionDictaminadora } from '../comision-dictaminadora.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComisionDictaminadora for edit and NewComisionDictaminadoraFormGroupInput for create.
 */
type ComisionDictaminadoraFormGroupInput = IComisionDictaminadora | PartialWithRequiredKeyOf<NewComisionDictaminadora>;

type ComisionDictaminadoraFormDefaults = Pick<NewComisionDictaminadora, 'id'>;

type ComisionDictaminadoraFormGroupContent = {
  id: FormControl<IComisionDictaminadora['id'] | NewComisionDictaminadora['id']>;
  nombre: FormControl<IComisionDictaminadora['nombre']>;
};

export type ComisionDictaminadoraFormGroup = FormGroup<ComisionDictaminadoraFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComisionDictaminadoraFormService {
  createComisionDictaminadoraFormGroup(
    comisionDictaminadora: ComisionDictaminadoraFormGroupInput = { id: null }
  ): ComisionDictaminadoraFormGroup {
    const comisionDictaminadoraRawValue = {
      ...this.getFormDefaults(),
      ...comisionDictaminadora,
    };
    return new FormGroup<ComisionDictaminadoraFormGroupContent>({
      id: new FormControl(
        { value: comisionDictaminadoraRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nombre: new FormControl(comisionDictaminadoraRawValue.nombre, {
        validators: [Validators.maxLength(100)],
      }),
    });
  }

  getComisionDictaminadora(form: ComisionDictaminadoraFormGroup): IComisionDictaminadora | NewComisionDictaminadora {
    return form.getRawValue() as IComisionDictaminadora | NewComisionDictaminadora;
  }

  resetForm(form: ComisionDictaminadoraFormGroup, comisionDictaminadora: ComisionDictaminadoraFormGroupInput): void {
    const comisionDictaminadoraRawValue = { ...this.getFormDefaults(), ...comisionDictaminadora };
    form.reset(
      {
        ...comisionDictaminadoraRawValue,
        id: { value: comisionDictaminadoraRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ComisionDictaminadoraFormDefaults {
    return {
      id: null,
    };
  }
}
