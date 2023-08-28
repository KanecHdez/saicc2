import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ComisionDictaminadoraFormService, ComisionDictaminadoraFormGroup } from './comision-dictaminadora-form.service';
import { IComisionDictaminadora } from '../comision-dictaminadora.model';
import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';

@Component({
  selector: 'jhi-comision-dictaminadora-update',
  templateUrl: './comision-dictaminadora-update.component.html',
})
export class ComisionDictaminadoraUpdateComponent implements OnInit {
  isSaving = false;
  comisionDictaminadora: IComisionDictaminadora | null = null;

  editForm: ComisionDictaminadoraFormGroup = this.comisionDictaminadoraFormService.createComisionDictaminadoraFormGroup();

  constructor(
    protected comisionDictaminadoraService: ComisionDictaminadoraService,
    protected comisionDictaminadoraFormService: ComisionDictaminadoraFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comisionDictaminadora }) => {
      this.comisionDictaminadora = comisionDictaminadora;
      if (comisionDictaminadora) {
        this.updateForm(comisionDictaminadora);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comisionDictaminadora = this.comisionDictaminadoraFormService.getComisionDictaminadora(this.editForm);
    if (comisionDictaminadora.id !== null) {
      this.subscribeToSaveResponse(this.comisionDictaminadoraService.update(comisionDictaminadora));
    } else {
      this.subscribeToSaveResponse(this.comisionDictaminadoraService.create(comisionDictaminadora));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComisionDictaminadora>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(comisionDictaminadora: IComisionDictaminadora): void {
    this.comisionDictaminadora = comisionDictaminadora;
    this.comisionDictaminadoraFormService.resetForm(this.editForm, comisionDictaminadora);
  }
}
