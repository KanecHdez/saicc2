import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AcademicoFormService, AcademicoFormGroup } from './academico-form.service';
import { IAcademico } from '../academico.model';
import { AcademicoService } from '../service/academico.service';

@Component({
  selector: 'jhi-academico-update',
  templateUrl: './academico-update.component.html',
})
export class AcademicoUpdateComponent implements OnInit {
  isSaving = false;
  academico: IAcademico | null = null;

  editForm: AcademicoFormGroup = this.academicoFormService.createAcademicoFormGroup();

  constructor(
    protected academicoService: AcademicoService,
    protected academicoFormService: AcademicoFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academico }) => {
      this.academico = academico;
      if (academico) {
        this.updateForm(academico);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const academico = this.academicoFormService.getAcademico(this.editForm);
    if (academico.id !== null) {
      this.subscribeToSaveResponse(this.academicoService.update(academico));
    } else {
      this.subscribeToSaveResponse(this.academicoService.create(academico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAcademico>>): void {
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

  protected updateForm(academico: IAcademico): void {
    this.academico = academico;
    this.academicoFormService.resetForm(this.editForm, academico);
  }
}
