import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDictamen } from '../dictamen.model';

@Component({
  selector: 'jhi-dictamen-detail',
  templateUrl: './dictamen-detail.component.html',
})
export class DictamenDetailComponent implements OnInit {
  dictamen: IDictamen | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dictamen }) => {
      this.dictamen = dictamen;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
