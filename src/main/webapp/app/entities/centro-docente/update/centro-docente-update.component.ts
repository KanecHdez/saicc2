import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CentroDocenteFormService, CentroDocenteFormGroup } from './centro-docente-form.service';
import { ICentroDocente } from '../centro-docente.model';
import { CentroDocenteService } from '../service/centro-docente.service';
import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';
import { ComisionDictaminadoraService } from 'app/entities/comision-dictaminadora/service/comision-dictaminadora.service';

@Component({
  selector: 'jhi-centro-docente-update',
  templateUrl: './centro-docente-update.component.html',
})
export class CentroDocenteUpdateComponent implements OnInit {
  isSaving = false;
  centroDocente: ICentroDocente | null = null;

  comisionDictaminadorasSharedCollection: IComisionDictaminadora[] = [];

  editForm: CentroDocenteFormGroup = this.centroDocenteFormService.createCentroDocenteFormGroup();

  constructor(
    protected centroDocenteService: CentroDocenteService,
    protected centroDocenteFormService: CentroDocenteFormService,
    protected comisionDictaminadoraService: ComisionDictaminadoraService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareComisionDictaminadora = (o1: IComisionDictaminadora | null, o2: IComisionDictaminadora | null): boolean =>
    this.comisionDictaminadoraService.compareComisionDictaminadora(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroDocente }) => {
      this.centroDocente = centroDocente;
      if (centroDocente) {
        this.updateForm(centroDocente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centroDocente = this.centroDocenteFormService.getCentroDocente(this.editForm);
    if (centroDocente.id !== null) {
      this.subscribeToSaveResponse(this.centroDocenteService.update(centroDocente));
    } else {
      this.subscribeToSaveResponse(this.centroDocenteService.create(centroDocente));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentroDocente>>): void {
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

  protected updateForm(centroDocente: ICentroDocente): void {
    this.centroDocente = centroDocente;
    this.centroDocenteFormService.resetForm(this.editForm, centroDocente);

    this.comisionDictaminadorasSharedCollection =
      this.comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing<IComisionDictaminadora>(
        this.comisionDictaminadorasSharedCollection,
        centroDocente.comisionDictaminadora
      );
  }

  protected loadRelationshipsOptions(): void {
    this.comisionDictaminadoraService
      .query()
      .pipe(map((res: HttpResponse<IComisionDictaminadora[]>) => res.body ?? []))
      .pipe(
        map((comisionDictaminadoras: IComisionDictaminadora[]) =>
          this.comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing<IComisionDictaminadora>(
            comisionDictaminadoras,
            this.centroDocente?.comisionDictaminadora
          )
        )
      )
      .subscribe(
        (comisionDictaminadoras: IComisionDictaminadora[]) => (this.comisionDictaminadorasSharedCollection = comisionDictaminadoras)
      );
  }
}
