import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PuestoFormService, PuestoFormGroup } from './puesto-form.service';
import { IPuesto } from '../puesto.model';
import { PuestoService } from '../service/puesto.service';

@Component({
  selector: 'jhi-puesto-update',
  templateUrl: './puesto-update.component.html',
})
export class PuestoUpdateComponent implements OnInit {
  isSaving = false;
  puesto: IPuesto | null = null;

  editForm: PuestoFormGroup = this.puestoFormService.createPuestoFormGroup();

  constructor(
    protected puestoService: PuestoService,
    protected puestoFormService: PuestoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puesto }) => {
      this.puesto = puesto;
      if (puesto) {
        this.updateForm(puesto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const puesto = this.puestoFormService.getPuesto(this.editForm);
    if (puesto.id !== null) {
      this.subscribeToSaveResponse(this.puestoService.update(puesto));
    } else {
      this.subscribeToSaveResponse(this.puestoService.create(puesto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPuesto>>): void {
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

  protected updateForm(puesto: IPuesto): void {
    this.puesto = puesto;
    this.puestoFormService.resetForm(this.editForm, puesto);
  }
}
