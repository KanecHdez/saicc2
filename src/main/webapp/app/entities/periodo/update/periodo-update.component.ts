import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { PeriodoFormService, PeriodoFormGroup } from './periodo-form.service';
import { IPeriodo } from '../periodo.model';
import { PeriodoService } from '../service/periodo.service';

@Component({
  selector: 'jhi-periodo-update',
  templateUrl: './periodo-update.component.html',
})
export class PeriodoUpdateComponent implements OnInit {
  isSaving = false;
  periodo: IPeriodo | null = null;

  editForm: PeriodoFormGroup = this.periodoFormService.createPeriodoFormGroup();

  constructor(
    protected periodoService: PeriodoService,
    protected periodoFormService: PeriodoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodo }) => {
      this.periodo = periodo;
      if (periodo) {
        this.updateForm(periodo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodo = this.periodoFormService.getPeriodo(this.editForm);
    if (periodo.id !== null) {
      this.subscribeToSaveResponse(this.periodoService.update(periodo));
    } else {
      this.subscribeToSaveResponse(this.periodoService.create(periodo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodo>>): void {
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

  protected updateForm(periodo: IPeriodo): void {
    this.periodo = periodo;
    this.periodoFormService.resetForm(this.editForm, periodo);
  }
}
