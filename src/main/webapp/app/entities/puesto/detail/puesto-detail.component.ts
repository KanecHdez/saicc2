import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPuesto } from '../puesto.model';

@Component({
  selector: 'jhi-puesto-detail',
  templateUrl: './puesto-detail.component.html',
})
export class PuestoDetailComponent implements OnInit {
  puesto: IPuesto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ puesto }) => {
      this.puesto = puesto;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
