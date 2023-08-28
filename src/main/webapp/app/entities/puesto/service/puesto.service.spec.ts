import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPuesto } from '../puesto.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../puesto.test-samples';

import { PuestoService, RestPuesto } from './puesto.service';

const requireRestSample: RestPuesto = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Puesto Service', () => {
  let service: PuestoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPuesto | IPuesto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PuestoService);
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

    it('should create a Puesto', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const puesto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(puesto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Puesto', () => {
      const puesto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(puesto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Puesto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Puesto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Puesto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPuestoToCollectionIfMissing', () => {
      it('should add a Puesto to an empty array', () => {
        const puesto: IPuesto = sampleWithRequiredData;
        expectedResult = service.addPuestoToCollectionIfMissing([], puesto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(puesto);
      });

      it('should not add a Puesto to an array that contains it', () => {
        const puesto: IPuesto = sampleWithRequiredData;
        const puestoCollection: IPuesto[] = [
          {
            ...puesto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPuestoToCollectionIfMissing(puestoCollection, puesto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Puesto to an array that doesn't contain it", () => {
        const puesto: IPuesto = sampleWithRequiredData;
        const puestoCollection: IPuesto[] = [sampleWithPartialData];
        expectedResult = service.addPuestoToCollectionIfMissing(puestoCollection, puesto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(puesto);
      });

      it('should add only unique Puesto to an array', () => {
        const puestoArray: IPuesto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const puestoCollection: IPuesto[] = [sampleWithRequiredData];
        expectedResult = service.addPuestoToCollectionIfMissing(puestoCollection, ...puestoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const puesto: IPuesto = sampleWithRequiredData;
        const puesto2: IPuesto = sampleWithPartialData;
        expectedResult = service.addPuestoToCollectionIfMissing([], puesto, puesto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(puesto);
        expect(expectedResult).toContain(puesto2);
      });

      it('should accept null and undefined values', () => {
        const puesto: IPuesto = sampleWithRequiredData;
        expectedResult = service.addPuestoToCollectionIfMissing([], null, puesto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(puesto);
      });

      it('should return initial array if no Puesto is added', () => {
        const puestoCollection: IPuesto[] = [sampleWithRequiredData];
        expectedResult = service.addPuestoToCollectionIfMissing(puestoCollection, undefined, null);
        expect(expectedResult).toEqual(puestoCollection);
      });
    });

    describe('comparePuesto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePuesto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePuesto(entity1, entity2);
        const compareResult2 = service.comparePuesto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePuesto(entity1, entity2);
        const compareResult2 = service.comparePuesto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePuesto(entity1, entity2);
        const compareResult2 = service.comparePuesto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
