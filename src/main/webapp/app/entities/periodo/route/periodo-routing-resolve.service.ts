import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriodo } from '../periodo.model';
import { PeriodoService } from '../service/periodo.service';

@Injectable({ providedIn: 'root' })
export class PeriodoRoutingResolveService implements Resolve<IPeriodo | null> {
  constructor(protected service: PeriodoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPeriodo | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((periodo: HttpResponse<IPeriodo>) => {
          if (periodo.body) {
            return of(periodo.body);
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
