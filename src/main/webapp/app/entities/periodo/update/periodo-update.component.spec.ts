import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PeriodoFormService } from './periodo-form.service';
import { PeriodoService } from '../service/periodo.service';
import { IPeriodo } from '../periodo.model';

import { PeriodoUpdateComponent } from './periodo-update.component';

describe('Periodo Management Update Component', () => {
  let comp: PeriodoUpdateComponent;
  let fixture: ComponentFixture<PeriodoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let periodoFormService: PeriodoFormService;
  let periodoService: PeriodoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PeriodoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PeriodoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PeriodoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    periodoFormService = TestBed.inject(PeriodoFormService);
    periodoService = TestBed.inject(PeriodoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const periodo: IPeriodo = { id: 456 };

      activatedRoute.data = of({ periodo });
      comp.ngOnInit();

      expect(comp.periodo).toEqual(periodo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodo>>();
      const periodo = { id: 123 };
      jest.spyOn(periodoFormService, 'getPeriodo').mockReturnValue(periodo);
      jest.spyOn(periodoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodo }));
      saveSubject.complete();

      // THEN
      expect(periodoFormService.getPeriodo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(periodoService.update).toHaveBeenCalledWith(expect.objectContaining(periodo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodo>>();
      const periodo = { id: 123 };
      jest.spyOn(periodoFormService, 'getPeriodo').mockReturnValue({ id: null });
      jest.spyOn(periodoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: periodo }));
      saveSubject.complete();

      // THEN
      expect(periodoFormService.getPeriodo).toHaveBeenCalled();
      expect(periodoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPeriodo>>();
      const periodo = { id: 123 };
      jest.spyOn(periodoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ periodo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(periodoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
