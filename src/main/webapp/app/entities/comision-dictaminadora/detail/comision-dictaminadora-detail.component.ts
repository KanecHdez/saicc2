import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComisionDictaminadora } from '../comision-dictaminadora.model';

@Component({
  selector: 'jhi-comision-dictaminadora-detail',
  templateUrl: './comision-dictaminadora-detail.component.html',
})
export class ComisionDictaminadoraDetailComponent implements OnInit {
  comisionDictaminadora: IComisionDictaminadora | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comisionDictaminadora }) => {
      this.comisionDictaminadora = comisionDictaminadora;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
