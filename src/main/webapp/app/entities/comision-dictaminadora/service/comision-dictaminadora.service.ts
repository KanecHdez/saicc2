import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComisionDictaminadora, NewComisionDictaminadora } from '../comision-dictaminadora.model';

export type PartialUpdateComisionDictaminadora = Partial<IComisionDictaminadora> & Pick<IComisionDictaminadora, 'id'>;

export type EntityResponseType = HttpResponse<IComisionDictaminadora>;
export type EntityArrayResponseType = HttpResponse<IComisionDictaminadora[]>;

@Injectable({ providedIn: 'root' })
export class ComisionDictaminadoraService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comision-dictaminadoras');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(comisionDictaminadora: NewComisionDictaminadora): Observable<EntityResponseType> {
    return this.http.post<IComisionDictaminadora>(this.resourceUrl, comisionDictaminadora, { observe: 'response' });
  }

  update(comisionDictaminadora: IComisionDictaminadora): Observable<EntityResponseType> {
    return this.http.put<IComisionDictaminadora>(
      `${this.resourceUrl}/${this.getComisionDictaminadoraIdentifier(comisionDictaminadora)}`,
      comisionDictaminadora,
      { observe: 'response' }
    );
  }

  partialUpdate(comisionDictaminadora: PartialUpdateComisionDictaminadora): Observable<EntityResponseType> {
    return this.http.patch<IComisionDictaminadora>(
      `${this.resourceUrl}/${this.getComisionDictaminadoraIdentifier(comisionDictaminadora)}`,
      comisionDictaminadora,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComisionDictaminadora>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComisionDictaminadora[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getComisionDictaminadoraIdentifier(comisionDictaminadora: Pick<IComisionDictaminadora, 'id'>): number {
    return comisionDictaminadora.id;
  }

  compareComisionDictaminadora(o1: Pick<IComisionDictaminadora, 'id'> | null, o2: Pick<IComisionDictaminadora, 'id'> | null): boolean {
    return o1 && o2 ? this.getComisionDictaminadoraIdentifier(o1) === this.getComisionDictaminadoraIdentifier(o2) : o1 === o2;
  }

  addComisionDictaminadoraToCollectionIfMissing<Type extends Pick<IComisionDictaminadora, 'id'>>(
    comisionDictaminadoraCollection: Type[],
    ...comisionDictaminadorasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comisionDictaminadoras: Type[] = comisionDictaminadorasToCheck.filter(isPresent);
    if (comisionDictaminadoras.length > 0) {
      const comisionDictaminadoraCollectionIdentifiers = comisionDictaminadoraCollection.map(
        comisionDictaminadoraItem => this.getComisionDictaminadoraIdentifier(comisionDictaminadoraItem)!
      );
      const comisionDictaminadorasToAdd = comisionDictaminadoras.filter(comisionDictaminadoraItem => {
        const comisionDictaminadoraIdentifier = this.getComisionDictaminadoraIdentifier(comisionDictaminadoraItem);
        if (comisionDictaminadoraCollectionIdentifiers.includes(comisionDictaminadoraIdentifier)) {
          return false;
        }
        comisionDictaminadoraCollectionIdentifiers.push(comisionDictaminadoraIdentifier);
        return true;
      });
      return [...comisionDictaminadorasToAdd, ...comisionDictaminadoraCollection];
    }
    return comisionDictaminadoraCollection;
  }
}
