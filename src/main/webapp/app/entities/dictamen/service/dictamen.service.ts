import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDictamen, NewDictamen } from '../dictamen.model';

export type PartialUpdateDictamen = Partial<IDictamen> & Pick<IDictamen, 'id'>;

type RestOf<T extends IDictamen | NewDictamen> = Omit<T, 'fechaPromocion' | 'createdDate' | 'lastModifiedDate'> & {
  fechaPromocion?: string | null;
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestDictamen = RestOf<IDictamen>;

export type NewRestDictamen = RestOf<NewDictamen>;

export type PartialUpdateRestDictamen = RestOf<PartialUpdateDictamen>;

export type EntityResponseType = HttpResponse<IDictamen>;
export type EntityArrayResponseType = HttpResponse<IDictamen[]>;

@Injectable({ providedIn: 'root' })
export class DictamenService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dictamen');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dictamen: NewDictamen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dictamen);
    return this.http
      .post<RestDictamen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(dictamen: IDictamen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dictamen);
    return this.http
      .put<RestDictamen>(`${this.resourceUrl}/${this.getDictamenIdentifier(dictamen)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(dictamen: PartialUpdateDictamen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dictamen);
    return this.http
      .patch<RestDictamen>(`${this.resourceUrl}/${this.getDictamenIdentifier(dictamen)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDictamen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDictamen[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDictamenIdentifier(dictamen: Pick<IDictamen, 'id'>): number {
    return dictamen.id;
  }

  compareDictamen(o1: Pick<IDictamen, 'id'> | null, o2: Pick<IDictamen, 'id'> | null): boolean {
    return o1 && o2 ? this.getDictamenIdentifier(o1) === this.getDictamenIdentifier(o2) : o1 === o2;
  }

  addDictamenToCollectionIfMissing<Type extends Pick<IDictamen, 'id'>>(
    dictamenCollection: Type[],
    ...dictamenToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dictamen: Type[] = dictamenToCheck.filter(isPresent);
    if (dictamen.length > 0) {
      const dictamenCollectionIdentifiers = dictamenCollection.map(dictamenItem => this.getDictamenIdentifier(dictamenItem)!);
      const dictamenToAdd = dictamen.filter(dictamenItem => {
        const dictamenIdentifier = this.getDictamenIdentifier(dictamenItem);
        if (dictamenCollectionIdentifiers.includes(dictamenIdentifier)) {
          return false;
        }
        dictamenCollectionIdentifiers.push(dictamenIdentifier);
        return true;
      });
      return [...dictamenToAdd, ...dictamenCollection];
    }
    return dictamenCollection;
  }

  protected convertDateFromClient<T extends IDictamen | NewDictamen | PartialUpdateDictamen>(dictamen: T): RestOf<T> {
    return {
      ...dictamen,
      fechaPromocion: dictamen.fechaPromocion?.format(DATE_FORMAT) ?? null,
      createdDate: dictamen.createdDate?.toJSON() ?? null,
      lastModifiedDate: dictamen.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDictamen: RestDictamen): IDictamen {
    return {
      ...restDictamen,
      fechaPromocion: restDictamen.fechaPromocion ? dayjs(restDictamen.fechaPromocion) : undefined,
      createdDate: restDictamen.createdDate ? dayjs(restDictamen.createdDate) : undefined,
      lastModifiedDate: restDictamen.lastModifiedDate ? dayjs(restDictamen.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDictamen>): HttpResponse<IDictamen> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDictamen[]>): HttpResponse<IDictamen[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
