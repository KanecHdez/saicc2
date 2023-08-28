import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademico } from '../academico.model';

@Component({
  selector: 'jhi-academico-detail',
  templateUrl: './academico-detail.component.html',
})
export class AcademicoDetailComponent implements OnInit {
  academico: IAcademico | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ academico }) => {
      this.academico = academico;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
