import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICentroDocente } from '../centro-docente.model';

@Component({
  selector: 'jhi-centro-docente-detail',
  templateUrl: './centro-docente-detail.component.html',
})
export class CentroDocenteDetailComponent implements OnInit {
  centroDocente: ICentroDocente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroDocente }) => {
      this.centroDocente = centroDocente;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
