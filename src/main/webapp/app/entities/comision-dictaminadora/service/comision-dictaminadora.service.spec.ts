import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComisionDictaminadora } from '../comision-dictaminadora.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../comision-dictaminadora.test-samples';

import { ComisionDictaminadoraService } from './comision-dictaminadora.service';

const requireRestSample: IComisionDictaminadora = {
  ...sampleWithRequiredData,
};

describe('ComisionDictaminadora Service', () => {
  let service: ComisionDictaminadoraService;
  let httpMock: HttpTestingController;
  let expectedResult: IComisionDictaminadora | IComisionDictaminadora[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ComisionDictaminadoraService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ComisionDictaminadora', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const comisionDictaminadora = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comisionDictaminadora).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ComisionDictaminadora', () => {
      const comisionDictaminadora = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comisionDictaminadora).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ComisionDictaminadora', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ComisionDictaminadora', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ComisionDictaminadora', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addComisionDictaminadoraToCollectionIfMissing', () => {
      it('should add a ComisionDictaminadora to an empty array', () => {
        const comisionDictaminadora: IComisionDictaminadora = sampleWithRequiredData;
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing([], comisionDictaminadora);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comisionDictaminadora);
      });

      it('should not add a ComisionDictaminadora to an array that contains it', () => {
        const comisionDictaminadora: IComisionDictaminadora = sampleWithRequiredData;
        const comisionDictaminadoraCollection: IComisionDictaminadora[] = [
          {
            ...comisionDictaminadora,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing(comisionDictaminadoraCollection, comisionDictaminadora);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ComisionDictaminadora to an array that doesn't contain it", () => {
        const comisionDictaminadora: IComisionDictaminadora = sampleWithRequiredData;
        const comisionDictaminadoraCollection: IComisionDictaminadora[] = [sampleWithPartialData];
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing(comisionDictaminadoraCollection, comisionDictaminadora);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comisionDictaminadora);
      });

      it('should add only unique ComisionDictaminadora to an array', () => {
        const comisionDictaminadoraArray: IComisionDictaminadora[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const comisionDictaminadoraCollection: IComisionDictaminadora[] = [sampleWithRequiredData];
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing(
          comisionDictaminadoraCollection,
          ...comisionDictaminadoraArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comisionDictaminadora: IComisionDictaminadora = sampleWithRequiredData;
        const comisionDictaminadora2: IComisionDictaminadora = sampleWithPartialData;
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing([], comisionDictaminadora, comisionDictaminadora2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comisionDictaminadora);
        expect(expectedResult).toContain(comisionDictaminadora2);
      });

      it('should accept null and undefined values', () => {
        const comisionDictaminadora: IComisionDictaminadora = sampleWithRequiredData;
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing([], null, comisionDictaminadora, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comisionDictaminadora);
      });

      it('should return initial array if no ComisionDictaminadora is added', () => {
        const comisionDictaminadoraCollection: IComisionDictaminadora[] = [sampleWithRequiredData];
        expectedResult = service.addComisionDictaminadoraToCollectionIfMissing(comisionDictaminadoraCollection, undefined, null);
        expect(expectedResult).toEqual(comisionDictaminadoraCollection);
      });
    });

    describe('compareComisionDictaminadora', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComisionDictaminadora(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComisionDictaminadora(entity1, entity2);
        const compareResult2 = service.compareComisionDictaminadora(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComisionDictaminadora(entity1, entity2);
        const compareResult2 = service.compareComisionDictaminadora(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComisionDictaminadora(entity1, entity2);
        const compareResult2 = service.compareComisionDictaminadora(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
