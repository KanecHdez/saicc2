import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComisionDictaminadora } from '../comision-dictaminadora.model';
import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';

@Injectable({ providedIn: 'root' })
export class ComisionDictaminadoraRoutingResolveService implements Resolve<IComisionDictaminadora | null> {
  constructor(protected service: ComisionDictaminadoraService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComisionDictaminadora | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((comisionDictaminadora: HttpResponse<IComisionDictaminadora>) => {
          if (comisionDictaminadora.body) {
            return of(comisionDictaminadora.body);
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
