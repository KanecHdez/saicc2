import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DictamenFormService, DictamenFormGroup } from './dictamen-form.service';
import { IDictamen } from '../dictamen.model';
import { DictamenService } from '../service/dictamen.service';
import { IAcademico } from 'app/entities/academico/academico.model';
import { AcademicoService } from 'app/entities/academico/service/academico.service';
import { IPuesto } from 'app/entities/puesto/puesto.model';
import { PuestoService } from 'app/entities/puesto/service/puesto.service';
import { IPeriodo } from 'app/entities/periodo/periodo.model';
import { PeriodoService } from 'app/entities/periodo/service/periodo.service';
import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';
import { ComisionDictaminadoraService } from 'app/entities/comision-dictaminadora/service/comision-dictaminadora.service';
import { ICentroDocente } from 'app/entities/centro-docente/centro-docente.model';
import { CentroDocenteService } from 'app/entities/centro-docente/service/centro-docente.service';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';
import { TabuladorPromocionService } from 'app/entities/tabulador-promocion/service/tabulador-promocion.service';

@Component({
  selector: 'jhi-dictamen-update',
  templateUrl: './dictamen-update.component.html',
})
export class DictamenUpdateComponent implements OnInit {
  isSaving = false;
  dictamen: IDictamen | null = null;

  academicosSharedCollection: IAcademico[] = [];
  puestosSharedCollection: IPuesto[] = [];
  periodosSharedCollection: IPeriodo[] = [];
  comisionDictaminadorasSharedCollection: IComisionDictaminadora[] = [];
  centroDocentesSharedCollection: ICentroDocente[] = [];
  tabuladorPromocionsSharedCollection: ITabuladorPromocion[] = [];

  editForm: DictamenFormGroup = this.dictamenFormService.createDictamenFormGroup();

  constructor(
    protected dictamenService: DictamenService,
    protected dictamenFormService: DictamenFormService,
    protected academicoService: AcademicoService,
    protected puestoService: PuestoService,
    protected periodoService: PeriodoService,
    protected comisionDictaminadoraService: ComisionDictaminadoraService,
    protected centroDocenteService: CentroDocenteService,
    protected tabuladorPromocionService: TabuladorPromocionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareAcademico = (o1: IAcademico | null, o2: IAcademico | null): boolean => this.academicoService.compareAcademico(o1, o2);

  comparePuesto = (o1: IPuesto | null, o2: IPuesto | null): boolean => this.puestoService.comparePuesto(o1, o2);

  comparePeriodo = (o1: IPeriodo | null, o2: IPeriodo | null): boolean => this.periodoService.comparePeriodo(o1, o2);

  compareComisionDictaminadora = (o1: IComisionDictaminadora | null, o2: IComisionDictaminadora | null): boolean =>
    this.comisionDictaminadoraService.compareComisionDictaminadora(o1, o2);

  compareCentroDocente = (o1: ICentroDocente | null, o2: ICentroDocente | null): boolean =>
    this.centroDocenteService.compareCentroDocente(o1, o2);

  compareTabuladorPromocion = (o1: ITabuladorPromocion | null, o2: ITabuladorPromocion | null): boolean =>
    this.tabuladorPromocionService.compareTabuladorPromocion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dictamen }) => {
      this.dictamen = dictamen;
      if (dictamen) {
        this.updateForm(dictamen);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dictamen = this.dictamenFormService.getDictamen(this.editForm);
    if (dictamen.id !== null) {
      this.subscribeToSaveResponse(this.dictamenService.update(dictamen));
    } else {
      this.subscribeToSaveResponse(this.dictamenService.create(dictamen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDictamen>>): void {
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

  protected updateForm(dictamen: IDictamen): void {
    this.dictamen = dictamen;
    this.dictamenFormService.resetForm(this.editForm, dictamen);

    this.academicosSharedCollection = this.academicoService.addAcademicoToCollectionIfMissing<IAcademico>(
      this.academicosSharedCollection,
      dictamen.academico
    );
    this.puestosSharedCollection = this.puestoService.addPuestoToCollectionIfMissing<IPuesto>(
      this.puestosSharedCollection,
      dictamen.puestoActual,
      dictamen.puestoSolicitado
    );
    this.periodosSharedCollection = this.periodoService.addPeriodoToCollectionIfMissing<IPeriodo>(
      this.periodosSharedCollection,
      dictamen.periodo
    );
    this.comisionDictaminadorasSharedCollection =
      this.comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing<IComisionDictaminadora>(
        this.comisionDictaminadorasSharedCollection,
        dictamen.comisionDictaminadora
      );
    this.centroDocentesSharedCollection = this.centroDocenteService.addCentroDocenteToCollectionIfMissing<ICentroDocente>(
      this.centroDocentesSharedCollection,
      dictamen.dependencia
    );
    this.tabuladorPromocionsSharedCollection =
      this.tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing<ITabuladorPromocion>(
        this.tabuladorPromocionsSharedCollection,
        dictamen.tabuladorPromocion
      );
  }

  protected loadRelationshipsOptions(): void {
    this.academicoService
      .query()
      .pipe(map((res: HttpResponse<IAcademico[]>) => res.body ?? []))
      .pipe(
        map((academicos: IAcademico[]) =>
          this.academicoService.addAcademicoToCollectionIfMissing<IAcademico>(academicos, this.dictamen?.academico)
        )
      )
      .subscribe((academicos: IAcademico[]) => (this.academicosSharedCollection = academicos));

    this.puestoService
      .query()
      .pipe(map((res: HttpResponse<IPuesto[]>) => res.body ?? []))
      .pipe(
        map((puestos: IPuesto[]) =>
          this.puestoService.addPuestoToCollectionIfMissing<IPuesto>(puestos, this.dictamen?.puestoActual, this.dictamen?.puestoSolicitado)
        )
      )
      .subscribe((puestos: IPuesto[]) => (this.puestosSharedCollection = puestos));

    this.periodoService
      .query()
      .pipe(map((res: HttpResponse<IPeriodo[]>) => res.body ?? []))
      .pipe(map((periodos: IPeriodo[]) => this.periodoService.addPeriodoToCollectionIfMissing<IPeriodo>(periodos, this.dictamen?.periodo)))
      .subscribe((periodos: IPeriodo[]) => (this.periodosSharedCollection = periodos));

    this.comisionDictaminadoraService
      .query()
      .pipe(map((res: HttpResponse<IComisionDictaminadora[]>) => res.body ?? []))
      .pipe(
        map((comisionDictaminadoras: IComisionDictaminadora[]) =>
          this.comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing<IComisionDictaminadora>(
            comisionDictaminadoras,
            this.dictamen?.comisionDictaminadora
          )
        )
      )
      .subscribe(
        (comisionDictaminadoras: IComisionDictaminadora[]) => (this.comisionDictaminadorasSharedCollection = comisionDictaminadoras)
      );

    this.centroDocenteService
      .query()
      .pipe(map((res: HttpResponse<ICentroDocente[]>) => res.body ?? []))
      .pipe(
        map((centroDocentes: ICentroDocente[]) =>
          this.centroDocenteService.addCentroDocenteToCollectionIfMissing<ICentroDocente>(centroDocentes, this.dictamen?.dependencia)
        )
      )
      .subscribe((centroDocentes: ICentroDocente[]) => (this.centroDocentesSharedCollection = centroDocentes));

    this.tabuladorPromocionService
      .query()
      .pipe(map((res: HttpResponse<ITabuladorPromocion[]>) => res.body ?? []))
      .pipe(
        map((tabuladorPromocions: ITabuladorPromocion[]) =>
          this.tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing<ITabuladorPromocion>(
            tabuladorPromocions,
            this.dictamen?.tabuladorPromocion
          )
        )
      )
      .subscribe((tabuladorPromocions: ITabuladorPromocion[]) => (this.tabuladorPromocionsSharedCollection = tabuladorPromocions));
  }
}
