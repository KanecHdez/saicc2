import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TabuladorPromocionFormService, TabuladorPromocionFormGroup } from './tabulador-promocion-form.service';
import { ITabuladorPromocion } from '../tabulador-promocion.model';
import { TabuladorPromocionService } from '../service/tabulador-promocion.service';

@Component({
  selector: 'jhi-tabulador-promocion-update',
  templateUrl: './tabulador-promocion-update.component.html',
})
export class TabuladorPromocionUpdateComponent implements OnInit {
  isSaving = false;
  tabuladorPromocion: ITabuladorPromocion | null = null;

  editForm: TabuladorPromocionFormGroup = this.tabuladorPromocionFormService.createTabuladorPromocionFormGroup();

  constructor(
    protected tabuladorPromocionService: TabuladorPromocionService,
    protected tabuladorPromocionFormService: TabuladorPromocionFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabuladorPromocion }) => {
      this.tabuladorPromocion = tabuladorPromocion;
      if (tabuladorPromocion) {
        this.updateForm(tabuladorPromocion);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tabuladorPromocion = this.tabuladorPromocionFormService.getTabuladorPromocion(this.editForm);
    if (tabuladorPromocion.id !== null) {
      this.subscribeToSaveResponse(this.tabuladorPromocionService.update(tabuladorPromocion));
    } else {
      this.subscribeToSaveResponse(this.tabuladorPromocionService.create(tabuladorPromocion));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITabuladorPromocion>>): void {
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

  protected updateForm(tabuladorPromocion: ITabuladorPromocion): void {
    this.tabuladorPromocion = tabuladorPromocion;
    this.tabuladorPromocionFormService.resetForm(this.editForm, tabuladorPromocion);
  }
}
