import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IComisionDictaminadora } from '../comision-dictaminadora.model';
import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';

import { ComisionDictaminadoraRoutingResolveService } from './comision-dictaminadora-routing-resolve.service';

describe('ComisionDictaminadora routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ComisionDictaminadoraRoutingResolveService;
  let service: ComisionDictaminadoraService;
  let resultComisionDictaminadora: IComisionDictaminadora | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ComisionDictaminadoraRoutingResolveService);
    service = TestBed.inject(ComisionDictaminadoraService);
    resultComisionDictaminadora = undefined;
  });

  describe('resolve', () => {
    it('should return IComisionDictaminadora returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComisionDictaminadora = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComisionDictaminadora).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComisionDictaminadora = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultComisionDictaminadora).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IComisionDictaminadora>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultComisionDictaminadora = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultComisionDictaminadora).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
