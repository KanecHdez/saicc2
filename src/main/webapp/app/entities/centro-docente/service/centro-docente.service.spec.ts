import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICentroDocente } from '../centro-docente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../centro-docente.test-samples';

import { CentroDocenteService } from './centro-docente.service';

const requireRestSample: ICentroDocente = {
  ...sampleWithRequiredData,
};

describe('CentroDocente Service', () => {
  let service: CentroDocenteService;
  let httpMock: HttpTestingController;
  let expectedResult: ICentroDocente | ICentroDocente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CentroDocenteService);
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

    it('should create a CentroDocente', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const centroDocente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(centroDocente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CentroDocente', () => {
      const centroDocente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(centroDocente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CentroDocente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CentroDocente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CentroDocente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCentroDocenteToCollectionIfMissing', () => {
      it('should add a CentroDocente to an empty array', () => {
        const centroDocente: ICentroDocente = sampleWithRequiredData;
        expectedResult = service.addCentroDocenteToCollectionIfMissing([], centroDocente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centroDocente);
      });

      it('should not add a CentroDocente to an array that contains it', () => {
        const centroDocente: ICentroDocente = sampleWithRequiredData;
        const centroDocenteCollection: ICentroDocente[] = [
          {
            ...centroDocente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCentroDocenteToCollectionIfMissing(centroDocenteCollection, centroDocente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CentroDocente to an array that doesn't contain it", () => {
        const centroDocente: ICentroDocente = sampleWithRequiredData;
        const centroDocenteCollection: ICentroDocente[] = [sampleWithPartialData];
        expectedResult = service.addCentroDocenteToCollectionIfMissing(centroDocenteCollection, centroDocente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centroDocente);
      });

      it('should add only unique CentroDocente to an array', () => {
        const centroDocenteArray: ICentroDocente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centroDocenteCollection: ICentroDocente[] = [sampleWithRequiredData];
        expectedResult = service.addCentroDocenteToCollectionIfMissing(centroDocenteCollection, ...centroDocenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const centroDocente: ICentroDocente = sampleWithRequiredData;
        const centroDocente2: ICentroDocente = sampleWithPartialData;
        expectedResult = service.addCentroDocenteToCollectionIfMissing([], centroDocente, centroDocente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centroDocente);
        expect(expectedResult).toContain(centroDocente2);
      });

      it('should accept null and undefined values', () => {
        const centroDocente: ICentroDocente = sampleWithRequiredData;
        expectedResult = service.addCentroDocenteToCollectionIfMissing([], null, centroDocente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centroDocente);
      });

      it('should return initial array if no CentroDocente is added', () => {
        const centroDocenteCollection: ICentroDocente[] = [sampleWithRequiredData];
        expectedResult = service.addCentroDocenteToCollectionIfMissing(centroDocenteCollection, undefined, null);
        expect(expectedResult).toEqual(centroDocenteCollection);
      });
    });

    describe('compareCentroDocente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCentroDocente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCentroDocente(entity1, entity2);
        const compareResult2 = service.compareCentroDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCentroDocente(entity1, entity2);
        const compareResult2 = service.compareCentroDocente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCentroDocente(entity1, entity2);
        const compareResult2 = service.compareCentroDocente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
