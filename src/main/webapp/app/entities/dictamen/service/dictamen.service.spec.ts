import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDictamen } from '../dictamen.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../dictamen.test-samples';

import { DictamenService, RestDictamen } from './dictamen.service';

const requireRestSample: RestDictamen = {
  ...sampleWithRequiredData,
  fechaPromocion: sampleWithRequiredData.fechaPromocion?.format(DATE_FORMAT),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Dictamen Service', () => {
  let service: DictamenService;
  let httpMock: HttpTestingController;
  let expectedResult: IDictamen | IDictamen[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DictamenService);
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

    it('should create a Dictamen', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dictamen = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dictamen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Dictamen', () => {
      const dictamen = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dictamen).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Dictamen', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Dictamen', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Dictamen', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDictamenToCollectionIfMissing', () => {
      it('should add a Dictamen to an empty array', () => {
        const dictamen: IDictamen = sampleWithRequiredData;
        expectedResult = service.addDictamenToCollectionIfMissing([], dictamen);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dictamen);
      });

      it('should not add a Dictamen to an array that contains it', () => {
        const dictamen: IDictamen = sampleWithRequiredData;
        const dictamenCollection: IDictamen[] = [
          {
            ...dictamen,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDictamenToCollectionIfMissing(dictamenCollection, dictamen);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Dictamen to an array that doesn't contain it", () => {
        const dictamen: IDictamen = sampleWithRequiredData;
        const dictamenCollection: IDictamen[] = [sampleWithPartialData];
        expectedResult = service.addDictamenToCollectionIfMissing(dictamenCollection, dictamen);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dictamen);
      });

      it('should add only unique Dictamen to an array', () => {
        const dictamenArray: IDictamen[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dictamenCollection: IDictamen[] = [sampleWithRequiredData];
        expectedResult = service.addDictamenToCollectionIfMissing(dictamenCollection, ...dictamenArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dictamen: IDictamen = sampleWithRequiredData;
        const dictamen2: IDictamen = sampleWithPartialData;
        expectedResult = service.addDictamenToCollectionIfMissing([], dictamen, dictamen2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dictamen);
        expect(expectedResult).toContain(dictamen2);
      });

      it('should accept null and undefined values', () => {
        const dictamen: IDictamen = sampleWithRequiredData;
        expectedResult = service.addDictamenToCollectionIfMissing([], null, dictamen, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dictamen);
      });

      it('should return initial array if no Dictamen is added', () => {
        const dictamenCollection: IDictamen[] = [sampleWithRequiredData];
        expectedResult = service.addDictamenToCollectionIfMissing(dictamenCollection, undefined, null);
        expect(expectedResult).toEqual(dictamenCollection);
      });
    });

    describe('compareDictamen', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDictamen(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDictamen(entity1, entity2);
        const compareResult2 = service.compareDictamen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDictamen(entity1, entity2);
        const compareResult2 = service.compareDictamen(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDictamen(entity1, entity2);
        const compareResult2 = service.compareDictamen(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
