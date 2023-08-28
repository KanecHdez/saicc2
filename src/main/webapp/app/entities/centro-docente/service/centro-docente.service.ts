import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICentroDocente, NewCentroDocente } from '../centro-docente.model';

export type PartialUpdateCentroDocente = Partial<ICentroDocente> & Pick<ICentroDocente, 'id'>;

export type EntityResponseType = HttpResponse<ICentroDocente>;
export type EntityArrayResponseType = HttpResponse<ICentroDocente[]>;

@Injectable({ providedIn: 'root' })
export class CentroDocenteService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centro-docentes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(centroDocente: NewCentroDocente): Observable<EntityResponseType> {
    return this.http.post<ICentroDocente>(this.resourceUrl, centroDocente, { observe: 'response' });
  }

  update(centroDocente: ICentroDocente): Observable<EntityResponseType> {
    return this.http.put<ICentroDocente>(`${this.resourceUrl}/${this.getCentroDocenteIdentifier(centroDocente)}`, centroDocente, {
      observe: 'response',
    });
  }

  partialUpdate(centroDocente: PartialUpdateCentroDocente): Observable<EntityResponseType> {
    return this.http.patch<ICentroDocente>(`${this.resourceUrl}/${this.getCentroDocenteIdentifier(centroDocente)}`, centroDocente, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentroDocente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentroDocente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCentroDocenteIdentifier(centroDocente: Pick<ICentroDocente, 'id'>): number {
    return centroDocente.id;
  }

  compareCentroDocente(o1: Pick<ICentroDocente, 'id'> | null, o2: Pick<ICentroDocente, 'id'> | null): boolean {
    return o1 && o2 ? this.getCentroDocenteIdentifier(o1) === this.getCentroDocenteIdentifier(o2) : o1 === o2;
  }

  addCentroDocenteToCollectionIfMissing<Type extends Pick<ICentroDocente, 'id'>>(
    centroDocenteCollection: Type[],
    ...centroDocentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centroDocentes: Type[] = centroDocentesToCheck.filter(isPresent);
    if (centroDocentes.length > 0) {
      const centroDocenteCollectionIdentifiers = centroDocenteCollection.map(
        centroDocenteItem => this.getCentroDocenteIdentifier(centroDocenteItem)!
      );
      const centroDocentesToAdd = centroDocentes.filter(centroDocenteItem => {
        const centroDocenteIdentifier = this.getCentroDocenteIdentifier(centroDocenteItem);
        if (centroDocenteCollectionIdentifiers.includes(centroDocenteIdentifier)) {
          return false;
        }
        centroDocenteCollectionIdentifiers.push(centroDocenteIdentifier);
        return true;
      });
      return [...centroDocentesToAdd, ...centroDocenteCollection];
    }
    return centroDocenteCollection;
  }
}
