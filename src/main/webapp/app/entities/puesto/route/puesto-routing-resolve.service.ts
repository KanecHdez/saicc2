import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPuesto } from '../puesto.model';
import { PuestoService } from '../service/puesto.service';

@Injectable({ providedIn: 'root' })
export class PuestoRoutingResolveService implements Resolve<IPuesto | null> {
  constructor(protected service: PuestoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPuesto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((puesto: HttpResponse<IPuesto>) => {
          if (puesto.body) {
            return of(puesto.body);
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
