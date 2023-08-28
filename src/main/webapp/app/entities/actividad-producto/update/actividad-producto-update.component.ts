import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ActividadProductoFormService, ActividadProductoFormGroup } from './actividad-producto-form.service';
import { IActividadProducto } from '../actividad-producto.model';
import { ActividadProductoService } from '../service/actividad-producto.service';
import { ITabuladorActividadProducto } from 'app/entities/tabulador-actividad-producto/tabulador-actividad-producto.model';
import { TabuladorActividadProductoService } from 'app/entities/tabulador-actividad-producto/service/tabulador-actividad-producto.service';
import { IDictamen } from 'app/entities/dictamen/dictamen.model';
import { DictamenService } from 'app/entities/dictamen/service/dictamen.service';

@Component({
  selector: 'jhi-actividad-producto-update',
  templateUrl: './actividad-producto-update.component.html',
})
export class ActividadProductoUpdateComponent implements OnInit {
  isSaving = false;
  actividadProducto: IActividadProducto | null = null;

  tabuladorActividadProductosSharedCollection: ITabuladorActividadProducto[] = [];
  dictamenSharedCollection: IDictamen[] = [];

  editForm: ActividadProductoFormGroup = this.actividadProductoFormService.createActividadProductoFormGroup();

  constructor(
    protected actividadProductoService: ActividadProductoService,
    protected actividadProductoFormService: ActividadProductoFormService,
    protected tabuladorActividadProductoService: TabuladorActividadProductoService,
    protected dictamenService: DictamenService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTabuladorActividadProducto = (o1: ITabuladorActividadProducto | null, o2: ITabuladorActividadProducto | null): boolean =>
    this.tabuladorActividadProductoService.compareTabuladorActividadProducto(o1, o2);

  compareDictamen = (o1: IDictamen | null, o2: IDictamen | null): boolean => this.dictamenService.compareDictamen(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actividadProducto }) => {
      this.actividadProducto = actividadProducto;
      if (actividadProducto) {
        this.updateForm(actividadProducto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actividadProducto = this.actividadProductoFormService.getActividadProducto(this.editForm);
    if (actividadProducto.id !== null) {
      this.subscribeToSaveResponse(this.actividadProductoService.update(actividadProducto));
    } else {
      this.subscribeToSaveResponse(this.actividadProductoService.create(actividadProducto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActividadProducto>>): void {
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

  protected updateForm(actividadProducto: IActividadProducto): void {
    this.actividadProducto = actividadProducto;
    this.actividadProductoFormService.resetForm(this.editForm, actividadProducto);

    this.tabuladorActividadProductosSharedCollection =
      this.tabuladorActividadProductoService.addTabuladorActividadProductoToCollectionIfMissing<ITabuladorActividadProducto>(
        this.tabuladorActividadProductosSharedCollection,
        actividadProducto.tabuladorActProd
      );
    this.dictamenSharedCollection = this.dictamenService.addDictamenToCollectionIfMissing<IDictamen>(
      this.dictamenSharedCollection,
      actividadProducto.dictamen
    );
  }

  protected loadRelationshipsOptions(): void {
    this.tabuladorActividadProductoService
      .query()
      .pipe(map((res: HttpResponse<ITabuladorActividadProducto[]>) => res.body ?? []))
      .pipe(
        map((tabuladorActividadProductos: ITabuladorActividadProducto[]) =>
          this.tabuladorActividadProductoService.addTabuladorActividadProductoToCollectionIfMissing<ITabuladorActividadProducto>(
            tabuladorActividadProductos,
            this.actividadProducto?.tabuladorActProd
          )
        )
      )
      .subscribe(
        (tabuladorActividadProductos: ITabuladorActividadProducto[]) =>
          (this.tabuladorActividadProductosSharedCollection = tabuladorActividadProductos)
      );

    this.dictamenService
      .query()
      .pipe(map((res: HttpResponse<IDictamen[]>) => res.body ?? []))
      .pipe(
        map((dictamen: IDictamen[]) =>
          this.dictamenService.addDictamenToCollectionIfMissing<IDictamen>(dictamen, this.actividadProducto?.dictamen)
        )
      )
      .subscribe((dictamen: IDictamen[]) => (this.dictamenSharedCollection = dictamen));
  }
}
