import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriodo, NewPeriodo } from '../periodo.model';

export type PartialUpdatePeriodo = Partial<IPeriodo> & Pick<IPeriodo, 'id'>;

type RestOf<T extends IPeriodo | NewPeriodo> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestPeriodo = RestOf<IPeriodo>;

export type NewRestPeriodo = RestOf<NewPeriodo>;

export type PartialUpdateRestPeriodo = RestOf<PartialUpdatePeriodo>;

export type EntityResponseType = HttpResponse<IPeriodo>;
export type EntityArrayResponseType = HttpResponse<IPeriodo[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(periodo: NewPeriodo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodo);
    return this.http
      .post<RestPeriodo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(periodo: IPeriodo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodo);
    return this.http
      .put<RestPeriodo>(`${this.resourceUrl}/${this.getPeriodoIdentifier(periodo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(periodo: PartialUpdatePeriodo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(periodo);
    return this.http
      .patch<RestPeriodo>(`${this.resourceUrl}/${this.getPeriodoIdentifier(periodo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPeriodo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPeriodo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPeriodoIdentifier(periodo: Pick<IPeriodo, 'id'>): number {
    return periodo.id;
  }

  comparePeriodo(o1: Pick<IPeriodo, 'id'> | null, o2: Pick<IPeriodo, 'id'> | null): boolean {
    return o1 && o2 ? this.getPeriodoIdentifier(o1) === this.getPeriodoIdentifier(o2) : o1 === o2;
  }

  addPeriodoToCollectionIfMissing<Type extends Pick<IPeriodo, 'id'>>(
    periodoCollection: Type[],
    ...periodosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const periodos: Type[] = periodosToCheck.filter(isPresent);
    if (periodos.length > 0) {
      const periodoCollectionIdentifiers = periodoCollection.map(periodoItem => this.getPeriodoIdentifier(periodoItem)!);
      const periodosToAdd = periodos.filter(periodoItem => {
        const periodoIdentifier = this.getPeriodoIdentifier(periodoItem);
        if (periodoCollectionIdentifiers.includes(periodoIdentifier)) {
          return false;
        }
        periodoCollectionIdentifiers.push(periodoIdentifier);
        return true;
      });
      return [...periodosToAdd, ...periodoCollection];
    }
    return periodoCollection;
  }

  protected convertDateFromClient<T extends IPeriodo | NewPeriodo | PartialUpdatePeriodo>(periodo: T): RestOf<T> {
    return {
      ...periodo,
      createdDate: periodo.createdDate?.toJSON() ?? null,
      lastModifiedDate: periodo.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPeriodo: RestPeriodo): IPeriodo {
    return {
      ...restPeriodo,
      createdDate: restPeriodo.createdDate ? dayjs(restPeriodo.createdDate) : undefined,
      lastModifiedDate: restPeriodo.lastModifiedDate ? dayjs(restPeriodo.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPeriodo>): HttpResponse<IPeriodo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPeriodo[]>): HttpResponse<IPeriodo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
