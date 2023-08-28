import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAcademico, NewAcademico } from '../academico.model';

export type PartialUpdateAcademico = Partial<IAcademico> & Pick<IAcademico, 'id'>;

type RestOf<T extends IAcademico | NewAcademico> = Omit<T, 'createdDate' | 'lastModifiedDate'> & {
  createdDate?: string | null;
  lastModifiedDate?: string | null;
};

export type RestAcademico = RestOf<IAcademico>;

export type NewRestAcademico = RestOf<NewAcademico>;

export type PartialUpdateRestAcademico = RestOf<PartialUpdateAcademico>;

export type EntityResponseType = HttpResponse<IAcademico>;
export type EntityArrayResponseType = HttpResponse<IAcademico[]>;

@Injectable({ providedIn: 'root' })
export class AcademicoService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/academicos');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(academico: NewAcademico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academico);
    return this.http
      .post<RestAcademico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(academico: IAcademico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academico);
    return this.http
      .put<RestAcademico>(`${this.resourceUrl}/${this.getAcademicoIdentifier(academico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(academico: PartialUpdateAcademico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(academico);
    return this.http
      .patch<RestAcademico>(`${this.resourceUrl}/${this.getAcademicoIdentifier(academico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAcademico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAcademico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAcademicoIdentifier(academico: Pick<IAcademico, 'id'>): number {
    return academico.id;
  }

  compareAcademico(o1: Pick<IAcademico, 'id'> | null, o2: Pick<IAcademico, 'id'> | null): boolean {
    return o1 && o2 ? this.getAcademicoIdentifier(o1) === this.getAcademicoIdentifier(o2) : o1 === o2;
  }

  addAcademicoToCollectionIfMissing<Type extends Pick<IAcademico, 'id'>>(
    academicoCollection: Type[],
    ...academicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const academicos: Type[] = academicosToCheck.filter(isPresent);
    if (academicos.length > 0) {
      const academicoCollectionIdentifiers = academicoCollection.map(academicoItem => this.getAcademicoIdentifier(academicoItem)!);
      const academicosToAdd = academicos.filter(academicoItem => {
        const academicoIdentifier = this.getAcademicoIdentifier(academicoItem);
        if (academicoCollectionIdentifiers.includes(academicoIdentifier)) {
          return false;
        }
        academicoCollectionIdentifiers.push(academicoIdentifier);
        return true;
      });
      return [...academicosToAdd, ...academicoCollection];
    }
    return academicoCollection;
  }

  protected convertDateFromClient<T extends IAcademico | NewAcademico | PartialUpdateAcademico>(academico: T): RestOf<T> {
    return {
      ...academico,
      createdDate: academico.createdDate?.toJSON() ?? null,
      lastModifiedDate: academico.lastModifiedDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAcademico: RestAcademico): IAcademico {
    return {
      ...restAcademico,
      createdDate: restAcademico.createdDate ? dayjs(restAcademico.createdDate) : undefined,
      lastModifiedDate: restAcademico.lastModifiedDate ? dayjs(restAcademico.lastModifiedDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAcademico>): HttpResponse<IAcademico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAcademico[]>): HttpResponse<IAcademico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
