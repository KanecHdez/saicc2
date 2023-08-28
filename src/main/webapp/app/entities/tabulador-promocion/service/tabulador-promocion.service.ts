import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITabuladorPromocion, NewTabuladorPromocion } from '../tabulador-promocion.model';

export type PartialUpdateTabuladorPromocion = Partial<ITabuladorPromocion> & Pick<ITabuladorPromocion, 'id'>;

type RestOf<T extends ITabuladorPromocion | NewTabuladorPromocion> = Omit<
  T,
  'inicioVigencia' | 'finVigencia' | 'createdDate' | 'lastModifiedDate'
> & {
  inicioVigencia?: string | null;
  finVigencia?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestTabuladorPromocion = RestOf<ITabuladorPromocion>;

export type NewRestTabuladorPromocion = RestOf<NewTabuladorPromocion>;

export type PartialUpdateRestTabuladorPromocion = RestOf<PartialUpdateTabuladorPromocion>;

export type EntityResponseType = HttpResponse<ITabuladorPromocion>;
export type EntityArrayResponseType = HttpResponse<ITabuladorPromocion[]>;

@Injectable({ providedIn: 'root' })
export class TabuladorPromocionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tabulador-promocions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tabuladorPromocion: NewTabuladorPromocion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorPromocion);
    return this.http
      .post<RestTabuladorPromocion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tabuladorPromocion: ITabuladorPromocion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorPromocion);
    return this.http
      .put<RestTabuladorPromocion>(`${this.resourceUrl}/${this.getTabuladorPromocionIdentifier(tabuladorPromocion)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tabuladorPromocion: PartialUpdateTabuladorPromocion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorPromocion);
    return this.http
      .patch<RestTabuladorPromocion>(`${this.resourceUrl}/${this.getTabuladorPromocionIdentifier(tabuladorPromocion)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTabuladorPromocion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTabuladorPromocion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTabuladorPromocionIdentifier(tabuladorPromocion: Pick<ITabuladorPromocion, 'id'>): number {
    return tabuladorPromocion.id;
  }

  compareTabuladorPromocion(o1: Pick<ITabuladorPromocion, 'id'> | null, o2: Pick<ITabuladorPromocion, 'id'> | null): boolean {
    return o1 && o2 ? this.getTabuladorPromocionIdentifier(o1) === this.getTabuladorPromocionIdentifier(o2) : o1 === o2;
  }

  addTabuladorPromocionToCollectionIfMissing<Type extends Pick<ITabuladorPromocion, 'id'>>(
    tabuladorPromocionCollection: Type[],
    ...tabuladorPromocionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tabuladorPromocions: Type[] = tabuladorPromocionsToCheck.filter(isPresent);
    if (tabuladorPromocions.length > 0) {
      const tabuladorPromocionCollectionIdentifiers = tabuladorPromocionCollection.map(
        tabuladorPromocionItem => this.getTabuladorPromocionIdentifier(tabuladorPromocionItem)!
      );
      const tabuladorPromocionsToAdd = tabuladorPromocions.filter(tabuladorPromocionItem => {
        const tabuladorPromocionIdentifier = this.getTabuladorPromocionIdentifier(tabuladorPromocionItem);
        if (tabuladorPromocionCollectionIdentifiers.includes(tabuladorPromocionIdentifier)) {
          return false;
        }
        tabuladorPromocionCollectionIdentifiers.push(tabuladorPromocionIdentifier);
        return true;
      });
      return [...tabuladorPromocionsToAdd, ...tabuladorPromocionCollection];
    }
    return tabuladorPromocionCollection;
  }

  protected convertDateFromClient<T extends ITabuladorPromocion | NewTabuladorPromocion | PartialUpdateTabuladorPromocion>(
    tabuladorPromocion: T
  ): RestOf<T> {
    return {
      ...tabuladorPromocion,
      inicioVigencia: tabuladorPromocion.inicioVigencia?.format(DATE_FORMAT) ?? null,
      finVigencia: tabuladorPromocion.finVigencia?.format(DATE_FORMAT) ?? null,
      createdDate: tabuladorPromocion.createdDate?.toJSON() ?? null,
      lastModifiedDate: tabuladorPromocion.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTabuladorPromocion: RestTabuladorPromocion): ITabuladorPromocion {
    return {
      ...restTabuladorPromocion,
      inicioVigencia: restTabuladorPromocion.inicioVigencia ? dayjs(restTabuladorPromocion.inicioVigencia) : undefined,
      finVigencia: restTabuladorPromocion.finVigencia ? dayjs(restTabuladorPromocion.finVigencia) : undefined,
      createdDate: restTabuladorPromocion.createdDate ? dayjs(restTabuladorPromocion.createdDate) : undefined,
      lastModifiedDate: restTabuladorPromocion.lastModifiedDate ? dayjs(restTabuladorPromocion.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTabuladorPromocion>): HttpResponse<ITabuladorPromocion> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTabuladorPromocion[]>): HttpResponse<ITabuladorPromocion[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
