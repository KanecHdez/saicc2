import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDictamen, NewDictamen } from '../dictamen.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDictamen for edit and NewDictamenFormGroupInput for create.
 */
type DictamenFormGroupInput = IDictamen | PartialWithRequiredKeyOf<NewDictamen>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDictamen | NewDictamen> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

type DictamenFormRawValue = FormValueOf<IDictamen>;

type NewDictamenFormRawValue = FormValueOf<NewDictamen>;

type DictamenFormDefaults = Pick<NewDictamen, 'id' | 'procede' | 'createdDate' | 'lastModifiedDate'>;

type DictamenFormGroupContent = {
  id: FormControl<DictamenFormRawValue['id'] | NewDictamen['id']>;
  noDictamen: FormControl<DictamenFormRawValue['noDictamen']>;
  fechaPromocion: FormControl<DictamenFormRawValue['fechaPromocion']>;
  puntosAlcanzados: FormControl<DictamenFormRawValue['puntosAlcanzados']>;
  puntosRequeridos: FormControl<DictamenFormRawValue['puntosRequeridos']>;
  puntosExcedentes: FormControl<DictamenFormRawValue['puntosExcedentes']>;
  puntosFaltantes: FormControl<DictamenFormRawValue['puntosFaltantes']>;
  puntosExcedentesAnterior: FormControl<DictamenFormRawValue['puntosExcedentesAnterior']>;
  puntosPuestoActual: FormControl<DictamenFormRawValue['puntosPuestoActual']>;
  puntosPuestoSolicitado: FormControl<DictamenFormRawValue['puntosPuestoSolicitado']>;
  procede: FormControl<DictamenFormRawValue['procede']>;
  numeroInstancia: FormControl<DictamenFormRawValue['numeroInstancia']>;
  folioHomologacion: FormControl<DictamenFormRawValue['folioHomologacion']>;
  modifiedBy: FormControl<DictamenFormRawValue['modifiedBy']>;
  createdDate: FormControl<DictamenFormRawValue['createdDate']>;
  lastModifiedDate: FormControl<DictamenFormRawValue['lastModifiedDate']>;
  academico: FormControl<DictamenFormRawValue['academico']>;
  puestoActual: FormControl<DictamenFormRawValue['puestoActual']>;
  puestoSolicitado: FormControl<DictamenFormRawValue['puestoSolicitado']>;
  periodo: FormControl<DictamenFormRawValue['periodo']>;
  comisionDictaminadora: FormControl<DictamenFormRawValue['comisionDictaminadora']>;
  dependencia: FormControl<DictamenFormRawValue['dependencia']>;
  tabuladorPromocion: FormControl<DictamenFormRawValue['tabuladorPromocion']>;
};

export type DictamenFormGroup = FormGroup<DictamenFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DictamenFormService {
  createDictamenFormGroup(dictamen: DictamenFormGroupInput = { id: null }): DictamenFormGroup {
    const dictamenRawValue = this.convertDictamenToDictamenRawValue({
      ...this.getFormDefaults(),
      ...dictamen,
    });
    return new FormGroup<DictamenFormGroupContent>({
      id: new FormControl(
        { value: dictamenRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      noDictamen: new FormControl(dictamenRawValue.noDictamen),
      fechaPromocion: new FormControl(dictamenRawValue.fechaPromocion),
      puntosAlcanzados: new FormControl(dictamenRawValue.puntosAlcanzados),
      puntosRequeridos: new FormControl(dictamenRawValue.puntosRequeridos),
      puntosExcedentes: new FormControl(dictamenRawValue.puntosExcedentes),
      puntosFaltantes: new FormControl(dictamenRawValue.puntosFaltantes),
      puntosExcedentesAnterior: new FormControl(dictamenRawValue.puntosExcedentesAnterior),
      puntosPuestoActual: new FormControl(dictamenRawValue.puntosPuestoActual),
      puntosPuestoSolicitado: new FormControl(dictamenRawValue.puntosPuestoSolicitado),
      procede: new FormControl(dictamenRawValue.procede),
      numeroInstancia: new FormControl(dictamenRawValue.numeroInstancia),
      folioHomologacion: new FormControl(dictamenRawValue.folioHomologacion, {
        validators: [Validators.maxLength(15)],
      }),
      modifiedBy: new FormControl(dictamenRawValue.modifiedBy, {
        validators: [Validators.maxLength(100)],
      }),
      createdDate: new FormControl(dictamenRawValue.createdDate),
      lastModifiedDate: new FormControl(dictamenRawValue.lastModifiedDate),
      academico: new FormControl(dictamenRawValue.academico),
      puestoActual: new FormControl(dictamenRawValue.puestoActual),
      puestoSolicitado: new FormControl(dictamenRawValue.puestoSolicitado),
      periodo: new FormControl(dictamenRawValue.periodo),
      comisionDictaminadora: new FormControl(dictamenRawValue.comisionDictaminadora),
      dependencia: new FormControl(dictamenRawValue.dependencia),
      tabuladorPromocion: new FormControl(dictamenRawValue.tabuladorPromocion),
    });
  }

  getDictamen(form: DictamenFormGroup): IDictamen | NewDictamen {
    return this.convertDictamenRawValueToDictamen(form.getRawValue() as DictamenFormRawValue | NewDictamenFormRawValue);
  }

  resetForm(form: DictamenFormGroup, dictamen: DictamenFormGroupInput): void {
    const dictamenRawValue = this.convertDictamenToDictamenRawValue({ ...this.getFormDefaults(), ...dictamen });
    form.reset(
      {
        ...dictamenRawValue,
        id: { value: dictamenRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DictamenFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      procede: false,
      createdDate: currentTime,
      lastModifiedDate: currentTime,
    };
  }

  private convertDictamenRawValueToDictamen(rawDictamen: DictamenFormRawValue | NewDictamenFormRawValue): IDictamen | NewDictamen {
    return {
      ...rawDictamen,
      createdDate: dayjs(rawDictamen.createdDate, DATE_TIME_FORMAT),
      lastModifiedDate: dayjs(rawDictamen.lastModifiedDate, DATE_TIME_FORMAT),
    };
  }

  private convertDictamenToDictamenRawValue(
    dictamen: IDictamen | (Partial<NewDictamen> & DictamenFormDefaults)
  ): DictamenFormRawValue | PartialWithRequiredKeyOf<NewDictamenFormRawValue> {
    return {
      ...dictamen,
      createdDate: dictamen.createdDate ? dictamen.createdDate.format(DATE_TIME_FORMAT) : undefined,
      lastModifiedDate: dictamen.lastModifiedDate ? dictamen.lastModifiedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
