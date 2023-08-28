import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';
import { TabuladorActividadProductoService } from '../service/tabulador-actividad-producto.service';

@Injectable({ providedIn: 'root' })
export class TabuladorActividadProductoRoutingResolveService implements Resolve<ITabuladorActividadProducto | null> {
  constructor(protected service: TabuladorActividadProductoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITabuladorActividadProducto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tabuladorActividadProducto: HttpResponse<ITabuladorActividadProducto>) => {
          if (tabuladorActividadProducto.body) {
            return of(tabuladorActividadProducto.body);
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
