import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../tabulador-actividad-producto.test-samples';

import { TabuladorActividadProductoService, RestTabuladorActividadProducto } from './tabulador-actividad-producto.service';

const requireRestSample: RestTabuladorActividadProducto = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('TabuladorActividadProducto Service', () => {
  let service: TabuladorActividadProductoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITabuladorActividadProducto | ITabuladorActividadProducto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TabuladorActividadProductoService);
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

    it('should create a TabuladorActividadProducto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tabuladorActividadProducto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tabuladorActividadProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TabuladorActividadProducto', () => {
      const tabuladorActividadProducto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tabuladorActividadProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TabuladorActividadProducto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TabuladorActividadProducto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TabuladorActividadProducto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTabuladorActividadProductoToCollectionIfMissing', () => {
      it('should add a TabuladorActividadProducto to an empty array', () => {
        const tabuladorActividadProducto: ITabuladorActividadProducto = sampleWithRequiredData;
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing([], tabuladorActividadProducto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tabuladorActividadProducto);
      });

      it('should not add a TabuladorActividadProducto to an array that contains it', () => {
        const tabuladorActividadProducto: ITabuladorActividadProducto = sampleWithRequiredData;
        const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [
          {
            ...tabuladorActividadProducto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing(
          tabuladorActividadProductoCollection,
          tabuladorActividadProducto
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TabuladorActividadProducto to an array that doesn't contain it", () => {
        const tabuladorActividadProducto: ITabuladorActividadProducto = sampleWithRequiredData;
        const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [sampleWithPartialData];
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing(
          tabuladorActividadProductoCollection,
          tabuladorActividadProducto
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tabuladorActividadProducto);
      });

      it('should add only unique TabuladorActividadProducto to an array', () => {
        const tabuladorActividadProductoArray: ITabuladorActividadProducto[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [sampleWithRequiredData];
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing(
          tabuladorActividadProductoCollection,
          ...tabuladorActividadProductoArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tabuladorActividadProducto: ITabuladorActividadProducto = sampleWithRequiredData;
        const tabuladorActividadProducto2: ITabuladorActividadProducto = sampleWithPartialData;
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing(
          [],
          tabuladorActividadProducto,
          tabuladorActividadProducto2
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tabuladorActividadProducto);
        expect(expectedResult).toContain(tabuladorActividadProducto2);
      });

      it('should accept null and undefined values', () => {
        const tabuladorActividadProducto: ITabuladorActividadProducto = sampleWithRequiredData;
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing([], null, tabuladorActividadProducto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tabuladorActividadProducto);
      });

      it('should return initial array if no TabuladorActividadProducto is added', () => {
        const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [sampleWithRequiredData];
        expectedResult = service.addTabuladorActividadProductoToCollectionIfMissing(tabuladorActividadProductoCollection, undefined, null);
        expect(expectedResult).toEqual(tabuladorActividadProductoCollection);
      });
    });

    describe('compareTabuladorActividadProducto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTabuladorActividadProducto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTabuladorActividadProducto(entity1, entity2);
        const compareResult2 = service.compareTabuladorActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTabuladorActividadProducto(entity1, entity2);
        const compareResult2 = service.compareTabuladorActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTabuladorActividadProducto(entity1, entity2);
        const compareResult2 = service.compareTabuladorActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
