import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TabuladorPromocionFormService } from './tabulador-promocion-form.service';
import { TabuladorPromocionService } from '../service/tabulador-promocion.service';
import { ITabuladorPromocion } from '../tabulador-promocion.model';

import { TabuladorPromocionUpdateComponent } from './tabulador-promocion-update.component';

describe('TabuladorPromocion Management Update Component', () => {
  let comp: TabuladorPromocionUpdateComponent;
  let fixture: ComponentFixture<TabuladorPromocionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tabuladorPromocionFormService: TabuladorPromocionFormService;
  let tabuladorPromocionService: TabuladorPromocionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TabuladorPromocionUpdateComponent],
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
      .overrideTemplate(TabuladorPromocionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TabuladorPromocionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tabuladorPromocionFormService = TestBed.inject(TabuladorPromocionFormService);
    tabuladorPromocionService = TestBed.inject(TabuladorPromocionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tabuladorPromocion: ITabuladorPromocion = { id: 456 };

      activatedRoute.data = of({ tabuladorPromocion });
      comp.ngOnInit();

      expect(comp.tabuladorPromocion).toEqual(tabuladorPromocion);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorPromocion>>();
      const tabuladorPromocion = { id: 123 };
      jest.spyOn(tabuladorPromocionFormService, 'getTabuladorPromocion').mockReturnValue(tabuladorPromocion);
      jest.spyOn(tabuladorPromocionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorPromocion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tabuladorPromocion }));
      saveSubject.complete();

      // THEN
      expect(tabuladorPromocionFormService.getTabuladorPromocion).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tabuladorPromocionService.update).toHaveBeenCalledWith(expect.objectContaining(tabuladorPromocion));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorPromocion>>();
      const tabuladorPromocion = { id: 123 };
      jest.spyOn(tabuladorPromocionFormService, 'getTabuladorPromocion').mockReturnValue({ id: null });
      jest.spyOn(tabuladorPromocionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorPromocion: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tabuladorPromocion }));
      saveSubject.complete();

      // THEN
      expect(tabuladorPromocionFormService.getTabuladorPromocion).toHaveBeenCalled();
      expect(tabuladorPromocionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorPromocion>>();
      const tabuladorPromocion = { id: 123 };
      jest.spyOn(tabuladorPromocionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorPromocion });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tabuladorPromocionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
