import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAcademico } from '../academico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../academico.test-samples';

import { AcademicoService, RestAcademico } from './academico.service';

const requireRestSample: RestAcademico = {
  ...sampleWithRequiredData,
  createdDate: sampleWithRequiredData.createdDate?.toJSON(),
  lastModifiedDate: sampleWithRequiredData.lastModifiedDate?.toJSON(),
};

describe('Academico Service', () => {
  let service: AcademicoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAcademico | IAcademico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AcademicoService);
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

    it('should create a Academico', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const academico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(academico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Academico', () => {
      const academico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(academico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Academico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Academico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Academico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAcademicoToCollectionIfMissing', () => {
      it('should add a Academico to an empty array', () => {
        const academico: IAcademico = sampleWithRequiredData;
        expectedResult = service.addAcademicoToCollectionIfMissing([], academico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(academico);
      });

      it('should not add a Academico to an array that contains it', () => {
        const academico: IAcademico = sampleWithRequiredData;
        const academicoCollection: IAcademico[] = [
          {
            ...academico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAcademicoToCollectionIfMissing(academicoCollection, academico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Academico to an array that doesn't contain it", () => {
        const academico: IAcademico = sampleWithRequiredData;
        const academicoCollection: IAcademico[] = [sampleWithPartialData];
        expectedResult = service.addAcademicoToCollectionIfMissing(academicoCollection, academico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(academico);
      });

      it('should add only unique Academico to an array', () => {
        const academicoArray: IAcademico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const academicoCollection: IAcademico[] = [sampleWithRequiredData];
        expectedResult = service.addAcademicoToCollectionIfMissing(academicoCollection, ...academicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const academico: IAcademico = sampleWithRequiredData;
        const academico2: IAcademico = sampleWithPartialData;
        expectedResult = service.addAcademicoToCollectionIfMissing([], academico, academico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(academico);
        expect(expectedResult).toContain(academico2);
      });

      it('should accept null and undefined values', () => {
        const academico: IAcademico = sampleWithRequiredData;
        expectedResult = service.addAcademicoToCollectionIfMissing([], null, academico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(academico);
      });

      it('should return initial array if no Academico is added', () => {
        const academicoCollection: IAcademico[] = [sampleWithRequiredData];
        expectedResult = service.addAcademicoToCollectionIfMissing(academicoCollection, undefined, null);
        expect(expectedResult).toEqual(academicoCollection);
      });
    });

    describe('compareAcademico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAcademico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAcademico(entity1, entity2);
        const compareResult2 = service.compareAcademico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAcademico(entity1, entity2);
        const compareResult2 = service.compareAcademico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAcademico(entity1, entity2);
        const compareResult2 = service.compareAcademico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
