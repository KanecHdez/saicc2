import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IActividadProducto } from '../actividad-producto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../actividad-producto.test-samples';

import { ActividadProductoService, RestActividadProducto } from './actividad-producto.service';

const requireRestSample: RestActividadProducto = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('ActividadProducto Service', () => {
  let service: ActividadProductoService;
  let httpMock: HttpTestingController;
  let expectedResult: IActividadProducto | IActividadProducto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ActividadProductoService);
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

    it('should create a ActividadProducto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const actividadProducto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(actividadProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ActividadProducto', () => {
      const actividadProducto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(actividadProducto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ActividadProducto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ActividadProducto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ActividadProducto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addActividadProductoToCollectionIfMissing', () => {
      it('should add a ActividadProducto to an empty array', () => {
        const actividadProducto: IActividadProducto = sampleWithRequiredData;
        expectedResult = service.addActividadProductoToCollectionIfMissing([], actividadProducto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(actividadProducto);
      });

      it('should not add a ActividadProducto to an array that contains it', () => {
        const actividadProducto: IActividadProducto = sampleWithRequiredData;
        const actividadProductoCollection: IActividadProducto[] = [
          {
            ...actividadProducto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addActividadProductoToCollectionIfMissing(actividadProductoCollection, actividadProducto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ActividadProducto to an array that doesn't contain it", () => {
        const actividadProducto: IActividadProducto = sampleWithRequiredData;
        const actividadProductoCollection: IActividadProducto[] = [sampleWithPartialData];
        expectedResult = service.addActividadProductoToCollectionIfMissing(actividadProductoCollection, actividadProducto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(actividadProducto);
      });

      it('should add only unique ActividadProducto to an array', () => {
        const actividadProductoArray: IActividadProducto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const actividadProductoCollection: IActividadProducto[] = [sampleWithRequiredData];
        expectedResult = service.addActividadProductoToCollectionIfMissing(actividadProductoCollection, ...actividadProductoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const actividadProducto: IActividadProducto = sampleWithRequiredData;
        const actividadProducto2: IActividadProducto = sampleWithPartialData;
        expectedResult = service.addActividadProductoToCollectionIfMissing([], actividadProducto, actividadProducto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(actividadProducto);
        expect(expectedResult).toContain(actividadProducto2);
      });

      it('should accept null and undefined values', () => {
        const actividadProducto: IActividadProducto = sampleWithRequiredData;
        expectedResult = service.addActividadProductoToCollectionIfMissing([], null, actividadProducto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(actividadProducto);
      });

      it('should return initial array if no ActividadProducto is added', () => {
        const actividadProductoCollection: IActividadProducto[] = [sampleWithRequiredData];
        expectedResult = service.addActividadProductoToCollectionIfMissing(actividadProductoCollection, undefined, null);
        expect(expectedResult).toEqual(actividadProductoCollection);
      });
    });

    describe('compareActividadProducto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareActividadProducto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareActividadProducto(entity1, entity2);
        const compareResult2 = service.compareActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareActividadProducto(entity1, entity2);
        const compareResult2 = service.compareActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareActividadProducto(entity1, entity2);
        const compareResult2 = service.compareActividadProducto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
