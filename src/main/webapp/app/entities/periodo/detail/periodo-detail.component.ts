import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodo } from '../periodo.model';

@Component({
  selector: 'jhi-periodo-detail',
  templateUrl: './periodo-detail.component.html',
})
export class PeriodoDetailComponent implements OnInit {
  periodo: IPeriodo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodo }) => {
      this.periodo = periodo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
