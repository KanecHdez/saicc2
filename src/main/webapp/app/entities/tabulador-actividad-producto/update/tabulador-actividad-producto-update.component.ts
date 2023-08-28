import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TabuladorActividadProductoFormService, TabuladorActividadProductoFormGroup } from './tabulador-actividad-producto-form.service';
import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';
import { TabuladorActividadProductoService } from '../service/tabulador-actividad-producto.service';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';
import { TabuladorPromocionService } from 'app/entities/tabulador-promocion/service/tabulador-promocion.service';

@Component({
  selector: 'jhi-tabulador-actividad-producto-update',
  templateUrl: './tabulador-actividad-producto-update.component.html',
})
export class TabuladorActividadProductoUpdateComponent implements OnInit {
  isSaving = false;
  tabuladorActividadProducto: ITabuladorActividadProducto | null = null;

  tabuladorActividadProductosSharedCollection: ITabuladorActividadProducto[] = [];
  tabuladorPromocionsSharedCollection: ITabuladorPromocion[] = [];

  editForm: TabuladorActividadProductoFormGroup = this.tabuladorActividadProductoFormService.createTabuladorActividadProductoFormGroup();

  constructor(
    protected tabuladorActividadProductoService: TabuladorActividadProductoService,
    protected tabuladorActividadProductoFormService: TabuladorActividadProductoFormService,
    protected tabuladorPromocionService: TabuladorPromocionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareTabuladorActividadProducto = (o1: ITabuladorActividadProducto | null, o2: ITabuladorActividadProducto | null): boolean =>
    this.tabuladorActividadProductoService.compareTabuladorActividadProducto(o1, o2);

  compareTabuladorPromocion = (o1: ITabuladorPromocion | null, o2: ITabuladorPromocion | null): boolean =>
    this.tabuladorPromocionService.compareTabuladorPromocion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabuladorActividadProducto }) => {
      this.tabuladorActividadProducto = tabuladorActividadProducto;
      if (tabuladorActividadProducto) {
        this.updateForm(tabuladorActividadProducto);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tabuladorActividadProducto = this.tabuladorActividadProductoFormService.getTabuladorActividadProducto(this.editForm);
    if (tabuladorActividadProducto.id !== null) {
      this.subscribeToSaveResponse(this.tabuladorActividadProductoService.update(tabuladorActividadProducto));
    } else {
      this.subscribeToSaveResponse(this.tabuladorActividadProductoService.create(tabuladorActividadProducto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITabuladorActividadProducto>>): void {
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

  protected updateForm(tabuladorActividadProducto: ITabuladorActividadProducto): void {
    this.tabuladorActividadProducto = tabuladorActividadProducto;
    this.tabuladorActividadProductoFormService.resetForm(this.editForm, tabuladorActividadProducto);

    this.tabuladorActividadProductosSharedCollection =
      this.tabuladorActividadProductoService.addTabuladorActividadProductoToCollectionIfMissing<ITabuladorActividadProducto>(
        this.tabuladorActividadProductosSharedCollection,
        tabuladorActividadProducto.tabuladorActSuperior
      );
    this.tabuladorPromocionsSharedCollection =
      this.tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing<ITabuladorPromocion>(
        this.tabuladorPromocionsSharedCollection,
        tabuladorActividadProducto.tabulador
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
            this.tabuladorActividadProducto?.tabuladorActSuperior
          )
        )
      )
      .subscribe(
        (tabuladorActividadProductos: ITabuladorActividadProducto[]) =>
          (this.tabuladorActividadProductosSharedCollection = tabuladorActividadProductos)
      );

    this.tabuladorPromocionService
      .query()
      .pipe(map((res: HttpResponse<ITabuladorPromocion[]>) => res.body ?? []))
      .pipe(
        map((tabuladorPromocions: ITabuladorPromocion[]) =>
          this.tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing<ITabuladorPromocion>(
            tabuladorPromocions,
            this.tabuladorActividadProducto?.tabulador
          )
        )
      )
      .subscribe((tabuladorPromocions: ITabuladorPromocion[]) => (this.tabuladorPromocionsSharedCollection = tabuladorPromocions));
  }
}
