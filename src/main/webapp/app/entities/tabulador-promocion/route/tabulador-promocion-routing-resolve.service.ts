import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITabuladorPromocion } from '../tabulador-promocion.model';
import { TabuladorPromocionService } from '../service/tabulador-promocion.service';

@Injectable({ providedIn: 'root' })
export class TabuladorPromocionRoutingResolveService implements Resolve<ITabuladorPromocion | null> {
  constructor(protected service: TabuladorPromocionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITabuladorPromocion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((tabuladorPromocion: HttpResponse<ITabuladorPromocion>) => {
          if (tabuladorPromocion.body) {
            return of(tabuladorPromocion.body);
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
