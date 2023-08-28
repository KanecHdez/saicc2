import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ComisionDictaminadoraFormService } from './comision-dictaminadora-form.service';
import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';
import { IComisionDictaminadora } from '../comision-dictaminadora.model';

import { ComisionDictaminadoraUpdateComponent } from './comision-dictaminadora-update.component';

describe('ComisionDictaminadora Management Update Component', () => {
  let comp: ComisionDictaminadoraUpdateComponent;
  let fixture: ComponentFixture<ComisionDictaminadoraUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let comisionDictaminadoraFormService: ComisionDictaminadoraFormService;
  let comisionDictaminadoraService: ComisionDictaminadoraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ComisionDictaminadoraUpdateComponent],
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
      .overrideTemplate(ComisionDictaminadoraUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComisionDictaminadoraUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    comisionDictaminadoraFormService = TestBed.inject(ComisionDictaminadoraFormService);
    comisionDictaminadoraService = TestBed.inject(ComisionDictaminadoraService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const comisionDictaminadora: IComisionDictaminadora = { id: 456 };

      activatedRoute.data = of({ comisionDictaminadora });
      comp.ngOnInit();

      expect(comp.comisionDictaminadora).toEqual(comisionDictaminadora);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComisionDictaminadora>>();
      const comisionDictaminadora = { id: 123 };
      jest.spyOn(comisionDictaminadoraFormService, 'getComisionDictaminadora').mockReturnValue(comisionDictaminadora);
      jest.spyOn(comisionDictaminadoraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comisionDictaminadora });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comisionDictaminadora }));
      saveSubject.complete();

      // THEN
      expect(comisionDictaminadoraFormService.getComisionDictaminadora).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(comisionDictaminadoraService.update).toHaveBeenCalledWith(expect.objectContaining(comisionDictaminadora));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComisionDictaminadora>>();
      const comisionDictaminadora = { id: 123 };
      jest.spyOn(comisionDictaminadoraFormService, 'getComisionDictaminadora').mockReturnValue({ id: null });
      jest.spyOn(comisionDictaminadoraService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comisionDictaminadora: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comisionDictaminadora }));
      saveSubject.complete();

      // THEN
      expect(comisionDictaminadoraFormService.getComisionDictaminadora).toHaveBeenCalled();
      expect(comisionDictaminadoraService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComisionDictaminadora>>();
      const comisionDictaminadora = { id: 123 };
      jest.spyOn(comisionDictaminadoraService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comisionDictaminadora });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(comisionDictaminadoraService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
