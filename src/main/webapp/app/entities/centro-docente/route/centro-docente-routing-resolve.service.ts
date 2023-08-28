import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentroDocente } from '../centro-docente.model';
import { CentroDocenteService } from '../service/centro-docente.service';

@Injectable({ providedIn: 'root' })
export class CentroDocenteRoutingResolveService implements Resolve<ICentroDocente | null> {
  constructor(protected service: CentroDocenteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICentroDocente | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((centroDocente: HttpResponse<ICentroDocente>) => {
          if (centroDocente.body) {
            return of(centroDocente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
