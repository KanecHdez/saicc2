import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActividadProducto, NewActividadProducto } from '../actividad-producto.model';

export type PartialUpdateActividadProducto = Partial<IActividadProducto> & Pick<IActividadProducto, 'id'>;

type RestOf<T extends IActividadProducto | NewActividadProducto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestActividadProducto = RestOf<IActividadProducto>;

export type NewRestActividadProducto = RestOf<NewActividadProducto>;

export type PartialUpdateRestActividadProducto = RestOf<PartialUpdateActividadProducto>;

export type EntityResponseType = HttpResponse<IActividadProducto>;
export type EntityArrayResponseType = HttpResponse<IActividadProducto[]>;

@Injectable({ providedIn: 'root' })
export class ActividadProductoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/actividad-productos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(actividadProducto: NewActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actividadProducto);
    return this.http
      .post<RestActividadProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(actividadProducto: IActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actividadProducto);
    return this.http
      .put<RestActividadProducto>(`${this.resourceUrl}/${this.getActividadProductoIdentifier(actividadProducto)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(actividadProducto: PartialUpdateActividadProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actividadProducto);
    return this.http
      .patch<RestActividadProducto>(`${this.resourceUrl}/${this.getActividadProductoIdentifier(actividadProducto)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestActividadProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestActividadProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActividadProductoIdentifier(actividadProducto: Pick<IActividadProducto, 'id'>): number {
    return actividadProducto.id;
  }

  compareActividadProducto(o1: Pick<IActividadProducto, 'id'> | null, o2: Pick<IActividadProducto, 'id'> | null): boolean {
    return o1 && o2 ? this.getActividadProductoIdentifier(o1) === this.getActividadProductoIdentifier(o2) : o1 === o2;
  }

  addActividadProductoToCollectionIfMissing<Type extends Pick<IActividadProducto, 'id'>>(
    actividadProductoCollection: Type[],
    ...actividadProductosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const actividadProductos: Type[] = actividadProductosToCheck.filter(isPresent);
    if (actividadProductos.length > 0) {
      const actividadProductoCollectionIdentifiers = actividadProductoCollection.map(
        actividadProductoItem => this.getActividadProductoIdentifier(actividadProductoItem)!
      );
      const actividadProductosToAdd = actividadProductos.filter(actividadProductoItem => {
        const actividadProductoIdentifier = this.getActividadProductoIdentifier(actividadProductoItem);
        if (actividadProductoCollectionIdentifiers.includes(actividadProductoIdentifier)) {
          return false;
        }
        actividadProductoCollectionIdentifiers.push(actividadProductoIdentifier);
        return true;
      });
      return [...actividadProductosToAdd, ...actividadProductoCollection];
    }
    return actividadProductoCollection;
  }

  protected convertDateFromClient<T extends IActividadProducto | NewActividadProducto | PartialUpdateActividadProducto>(
    actividadProducto: T
  ): RestOf<T> {
    return {
      ...actividadProducto,
      createdDate: actividadProducto.createdDate?.toJSON() ?? null,
      lastModifiedDate: actividadProducto.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restActividadProducto: RestActividadProducto): IActividadProducto {
    return {
      ...restActividadProducto,
      createdDate: restActividadProducto.createdDate ? dayjs(restActividadProducto.createdDate) : undefined,
      lastModifiedDate: restActividadProducto.lastModifiedDate ? dayjs(restActividadProducto.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestActividadProducto>): HttpResponse<IActividadProducto> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestActividadProducto[]>): HttpResponse<IActividadProducto[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
