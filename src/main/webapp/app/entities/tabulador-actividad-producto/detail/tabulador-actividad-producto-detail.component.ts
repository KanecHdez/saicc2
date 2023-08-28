import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';

@Component({
  selector: 'jhi-tabulador-actividad-producto-detail',
  templateUrl: './tabulador-actividad-producto-detail.component.html',
})
export class TabuladorActividadProductoDetailComponent implements OnInit {
  tabuladorActividadProducto: ITabuladorActividadProducto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabuladorActividadProducto }) => {
      this.tabuladorActividadProducto = tabuladorActividadProducto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
