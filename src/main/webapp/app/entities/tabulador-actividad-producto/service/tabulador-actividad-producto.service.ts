import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITabuladorActividadProducto, NewTabuladorActividadProducto } from '../tabulador-actividad-producto.model';

export type PartialUpdateTabuladorActividadProducto = Partial<ITabuladorActividadProducto> & Pick<ITabuladorActividadProducto, 'id'>;

type RestOf<T extends ITabuladorActividadProducto | NewTabuladorActividadProducto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestTabuladorActividadProducto = RestOf<ITabuladorActividadProducto>;

export type NewRestTabuladorActividadProducto = RestOf<NewTabuladorActividadProducto>;

export type PartialUpdateRestTabuladorActividadProducto = RestOf<PartialUpdateTabuladorActividadProducto>;

export type EntityResponseType = HttpResponse<ITabuladorActividadProducto>;
export type EntityArrayResponseType = HttpResponse<ITabuladorActividadProducto[]>;

@Injectable({ providedIn: 'root' })
export class TabuladorActividadProductoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tabulador-actividad-productos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(tabuladorActividadProducto: NewTabuladorActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorActividadProducto);
    return this.http
      .post<RestTabuladorActividadProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tabuladorActividadProducto: ITabuladorActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorActividadProducto);
    return this.http
      .put<RestTabuladorActividadProducto>(
        `${this.resourceUrl}/${this.getTabuladorActividadProductoIdentifier(tabuladorActividadProducto)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tabuladorActividadProducto: PartialUpdateTabuladorActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tabuladorActividadProducto);
    return this.http
      .patch<RestTabuladorActividadProducto>(
        `${this.resourceUrl}/${this.getTabuladorActividadProductoIdentifier(tabuladorActividadProducto)}`,
        copy,
        { observe: 'response' }
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTabuladorActividadProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTabuladorActividadProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTabuladorActividadProductoIdentifier(tabuladorActividadProducto: Pick<ITabuladorActividadProducto, 'id'>): number {
    return tabuladorActividadProducto.id;
  }

  compareTabuladorActividadProducto(
    o1: Pick<ITabuladorActividadProducto, 'id'> | null,
    o2: Pick<ITabuladorActividadProducto, 'id'> | null
  ): boolean {
    return o1 && o2 ? this.getTabuladorActividadProductoIdentifier(o1) === this.getTabuladorActividadProductoIdentifier(o2) : o1 === o2;
  }

  addTabuladorActividadProductoToCollectionIfMissing<Type extends Pick<ITabuladorActividadProducto, 'id'>>(
    tabuladorActividadProductoCollection: Type[],
    ...tabuladorActividadProductosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tabuladorActividadProductos: Type[] = tabuladorActividadProductosToCheck.filter(isPresent);
    if (tabuladorActividadProductos.length > 0) {
      const tabuladorActividadProductoCollectionIdentifiers = tabuladorActividadProductoCollection.map(
        tabuladorActividadProductoItem => this.getTabuladorActividadProductoIdentifier(tabuladorActividadProductoItem)!
      );
      const tabuladorActividadProductosToAdd = tabuladorActividadProductos.filter(tabuladorActividadProductoItem => {
        const tabuladorActividadProductoIdentifier = this.getTabuladorActividadProductoIdentifier(tabuladorActividadProductoItem);
        if (tabuladorActividadProductoCollectionIdentifiers.includes(tabuladorActividadProductoIdentifier)) {
          return false;
        }
        tabuladorActividadProductoCollectionIdentifiers.push(tabuladorActividadProductoIdentifier);
        return true;
      });
      return [...tabuladorActividadProductosToAdd, ...tabuladorActividadProductoCollection];
    }
    return tabuladorActividadProductoCollection;
  }

  protected convertDateFromClient<
    T extends ITabuladorActividadProducto | NewTabuladorActividadProducto | PartialUpdateTabuladorActividadProducto
  >(tabuladorActividadProducto: T): RestOf<T> {
    return {
      ...tabuladorActividadProducto,
      createdDate: tabuladorActividadProducto.createdDate?.toJSON() ?? null,
      lastModifiedDate: tabuladorActividadProducto.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTabuladorActividadProducto: RestTabuladorActividadProducto): ITabuladorActividadProducto {
    return {
      ...restTabuladorActividadProducto,
      createdDate: restTabuladorActividadProducto.createdDate ? dayjs(restTabuladorActividadProducto.createdDate) : undefined,
      lastModifiedDate: restTabuladorActividadProducto.lastModifiedDate
        ? dayjs(restTabuladorActividadProducto.lastModifiedDate)
        : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTabuladorActividadProducto>): HttpResponse<ITabuladorActividadProducto> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestTabuladorActividadProducto[]>
  ): HttpResponse<ITabuladorActividadProducto[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
