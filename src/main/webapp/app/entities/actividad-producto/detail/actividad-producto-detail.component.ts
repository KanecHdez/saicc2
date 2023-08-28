import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActividadProducto } from '../actividad-producto.model';

@Component({
  selector: 'jhi-actividad-producto-detail',
  templateUrl: './actividad-producto-detail.component.html',
})
export class ActividadProductoDetailComponent implements OnInit {
  actividadProducto: IActividadProducto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actividadProducto }) => {
      this.actividadProducto = actividadProducto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
