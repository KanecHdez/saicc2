import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITabuladorPromocion } from '../tabulador-promocion.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tabulador-promocion.test-samples';

import { TabuladorPromocionService, RestTabuladorPromocion } from './tabulador-promocion.service';

const requireRestSample: RestTabuladorPromocion = {
  ...sampleWithRequiredData,
  inicioVigencia: sampleWithRequiredData.inicioVigencia?.format(DATE_FORMAT),
  finVigencia: sampleWithRequiredData.finVigencia?.format(DATE_FORMAT),
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('TabuladorPromocion Service', () => {
  let service: TabuladorPromocionService;
  let httpMock: HttpTestingController;
  let expectedResult: ITabuladorPromocion | ITabuladorPromocion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TabuladorPromocionService);
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

    it('should create a TabuladorPromocion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tabuladorPromocion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tabuladorPromocion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TabuladorPromocion', () => {
      const tabuladorPromocion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tabuladorPromocion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TabuladorPromocion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TabuladorPromocion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TabuladorPromocion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTabuladorPromocionToCollectionIfMissing', () => {
      it('should add a TabuladorPromocion to an empty array', () => {
        const tabuladorPromocion: ITabuladorPromocion = sampleWithRequiredData;
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing([], tabuladorPromocion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tabuladorPromocion);
      });

      it('should not add a TabuladorPromocion to an array that contains it', () => {
        const tabuladorPromocion: ITabuladorPromocion = sampleWithRequiredData;
        const tabuladorPromocionCollection: ITabuladorPromocion[] = [
          {
            ...tabuladorPromocion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing(tabuladorPromocionCollection, tabuladorPromocion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TabuladorPromocion to an array that doesn't contain it", () => {
        const tabuladorPromocion: ITabuladorPromocion = sampleWithRequiredData;
        const tabuladorPromocionCollection: ITabuladorPromocion[] = [sampleWithPartialData];
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing(tabuladorPromocionCollection, tabuladorPromocion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tabuladorPromocion);
      });

      it('should add only unique TabuladorPromocion to an array', () => {
        const tabuladorPromocionArray: ITabuladorPromocion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tabuladorPromocionCollection: ITabuladorPromocion[] = [sampleWithRequiredData];
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing(tabuladorPromocionCollection, ...tabuladorPromocionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tabuladorPromocion: ITabuladorPromocion = sampleWithRequiredData;
        const tabuladorPromocion2: ITabuladorPromocion = sampleWithPartialData;
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing([], tabuladorPromocion, tabuladorPromocion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tabuladorPromocion);
        expect(expectedResult).toContain(tabuladorPromocion2);
      });

      it('should accept null and undefined values', () => {
        const tabuladorPromocion: ITabuladorPromocion = sampleWithRequiredData;
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing([], null, tabuladorPromocion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tabuladorPromocion);
      });

      it('should return initial array if no TabuladorPromocion is added', () => {
        const tabuladorPromocionCollection: ITabuladorPromocion[] = [sampleWithRequiredData];
        expectedResult = service.addTabuladorPromocionToCollectionIfMissing(tabuladorPromocionCollection, undefined, null);
        expect(expectedResult).toEqual(tabuladorPromocionCollection);
      });
    });

    describe('compareTabuladorPromocion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTabuladorPromocion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTabuladorPromocion(entity1, entity2);
        const compareResult2 = service.compareTabuladorPromocion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTabuladorPromocion(entity1, entity2);
        const compareResult2 = service.compareTabuladorPromocion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTabuladorPromocion(entity1, entity2);
        const compareResult2 = service.compareTabuladorPromocion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
