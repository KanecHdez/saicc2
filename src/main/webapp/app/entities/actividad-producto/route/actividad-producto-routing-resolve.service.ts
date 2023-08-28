import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IActividadProducto } from '../actividad-producto.model';
import { ActividadProductoService } from '../service/actividad-producto.service';

@Injectable({ providedIn: 'root' })
export class ActividadProductoRoutingResolveService implements Resolve<IActividadProducto | null> {
  constructor(protected service: ActividadProductoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActividadProducto | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((actividadProducto: HttpResponse<IActividadProducto>) => {
          if (actividadProducto.body) {
            return of(actividadProducto.body);
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
