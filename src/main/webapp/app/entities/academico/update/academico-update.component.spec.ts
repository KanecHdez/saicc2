import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AcademicoFormService } from './academico-form.service';
import { AcademicoService } from '../service/academico.service';
import { IAcademico } from '../academico.model';

import { AcademicoUpdateComponent } from './academico-update.component';

describe('Academico Management Update Component', () => {
  let comp: AcademicoUpdateComponent;
  let fixture: ComponentFixture<AcademicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let academicoFormService: AcademicoFormService;
  let academicoService: AcademicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AcademicoUpdateComponent],
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
      .overrideTemplate(AcademicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AcademicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    academicoFormService = TestBed.inject(AcademicoFormService);
    academicoService = TestBed.inject(AcademicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const academico: IAcademico = { id: 456 };

      activatedRoute.data = of({ academico });
      comp.ngOnInit();

      expect(comp.academico).toEqual(academico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcademico>>();
      const academico = { id: 123 };
      jest.spyOn(academicoFormService, 'getAcademico').mockReturnValue(academico);
      jest.spyOn(academicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: academico }));
      saveSubject.complete();

      // THEN
      expect(academicoFormService.getAcademico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(academicoService.update).toHaveBeenCalledWith(expect.objectContaining(academico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcademico>>();
      const academico = { id: 123 };
      jest.spyOn(academicoFormService, 'getAcademico').mockReturnValue({ id: null });
      jest.spyOn(academicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: academico }));
      saveSubject.complete();

      // THEN
      expect(academicoFormService.getAcademico).toHaveBeenCalled();
      expect(academicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAcademico>>();
      const academico = { id: 123 };
      jest.spyOn(academicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ academico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(academicoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
