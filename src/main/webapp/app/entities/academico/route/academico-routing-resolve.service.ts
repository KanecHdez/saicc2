import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAcademico } from '../academico.model';
import { AcademicoService } from '../service/academico.service';

@Injectable({ providedIn: 'root' })
export class AcademicoRoutingResolveService implements Resolve<IAcademico | null> {
  constructor(protected service: AcademicoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAcademico | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((academico: HttpResponse<IAcademico>) => {
          if (academico.body) {
            return of(academico.body);
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
