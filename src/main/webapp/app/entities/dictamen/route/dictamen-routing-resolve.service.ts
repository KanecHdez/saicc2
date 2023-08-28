import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDictamen } from '../dictamen.model';
import { DictamenService } from '../service/dictamen.service';

@Injectable({ providedIn: 'root' })
export class DictamenRoutingResolveService implements Resolve<IDictamen | null> {
  constructor(protected service: DictamenService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDictamen | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((dictamen: HttpResponse<IDictamen>) => {
          if (dictamen.body) {
            return of(dictamen.body);
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
