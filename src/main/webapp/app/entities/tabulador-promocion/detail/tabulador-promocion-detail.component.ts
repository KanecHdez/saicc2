import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITabuladorPromocion } from '../tabulador-promocion.model';

@Component({
  selector: 'jhi-tabulador-promocion-detail',
  templateUrl: './tabulador-promocion-detail.component.html',
})
export class TabuladorPromocionDetailComponent implements OnInit {
  tabuladorPromocion: ITabuladorPromocion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tabuladorPromocion }) => {
      this.tabuladorPromocion = tabuladorPromocion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
