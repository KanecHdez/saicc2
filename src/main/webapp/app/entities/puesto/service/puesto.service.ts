import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPuesto, NewPuesto } from '../puesto.model';

export type PartialUpdatePuesto = Partial<IPuesto> & Pick<IPuesto, 'id'>;

type RestOf<T extends IPuesto | NewPuesto> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestPuesto = RestOf<IPuesto>;

export type NewRestPuesto = RestOf<NewPuesto>;

export type PartialUpdateRestPuesto = RestOf<PartialUpdatePuesto>;

export type EntityResponseType = HttpResponse<IPuesto>;
export type EntityArrayResponseType = HttpResponse<IPuesto[]>;

@Injectable({ providedIn: 'root' })
export class PuestoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/puestos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(puesto: NewPuesto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(puesto);
    return this.http
      .post<RestPuesto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(puesto: IPuesto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(puesto);
    return this.http
      .put<RestPuesto>(`${this.resourceUrl}/${this.getPuestoIdentifier(puesto)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(puesto: PartialUpdatePuesto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(puesto);
    return this.http
      .patch<RestPuesto>(`${this.resourceUrl}/${this.getPuestoIdentifier(puesto)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPuesto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPuesto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPuestoIdentifier(puesto: Pick<IPuesto, 'id'>): number {
    return puesto.id;
  }

  comparePuesto(o1: Pick<IPuesto, 'id'> | null, o2: Pick<IPuesto, 'id'> | null): boolean {
    return o1 && o2 ? this.getPuestoIdentifier(o1) === this.getPuestoIdentifier(o2) : o1 === o2;
  }

  addPuestoToCollectionIfMissing<Type extends Pick<IPuesto, 'id'>>(
    puestoCollection: Type[],
    ...puestosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const puestos: Type[] = puestosToCheck.filter(isPresent);
    if (puestos.length > 0) {
      const puestoCollectionIdentifiers = puestoCollection.map(puestoItem => this.getPuestoIdentifier(puestoItem)!);
      const puestosToAdd = puestos.filter(puestoItem => {
        const puestoIdentifier = this.getPuestoIdentifier(puestoItem);
        if (puestoCollectionIdentifiers.includes(puestoIdentifier)) {
          return false;
        }
        puestoCollectionIdentifiers.push(puestoIdentifier);
        return true;
      });
      return [...puestosToAdd, ...puestoCollection];
    }
    return puestoCollection;
  }

  protected convertDateFromClient<T extends IPuesto | NewPuesto | PartialUpdatePuesto>(puesto: T): RestOf<T> {
    return {
      ...puesto,
      createdDate: puesto.createdDate?.toJSON() ?? null,
      lastModifiedDate: puesto.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPuesto: RestPuesto): IPuesto {
    return {
      ...restPuesto,
      createdDate: restPuesto.createdDate ? dayjs(restPuesto.createdDate) : undefined,
      lastModifiedDate: restPuesto.lastModifiedDate ? dayjs(restPuesto.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPuesto>): HttpResponse<IPuesto> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPuesto[]>): HttpResponse<IPuesto[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
