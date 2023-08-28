import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CentroDocenteFormService } from './centro-docente-form.service';
import { CentroDocenteService } from '../service/centro-docente.service';
import { ICentroDocente } from '../centro-docente.model';
import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';
import { ComisionDictaminadoraService } from 'app/entities/comision-dictaminadora/service/comision-dictaminadora.service';

import { CentroDocenteUpdateComponent } from './centro-docente-update.component';

describe('CentroDocente Management Update Component', () => {
  let comp: CentroDocenteUpdateComponent;
  let fixture: ComponentFixture<CentroDocenteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centroDocenteFormService: CentroDocenteFormService;
  let centroDocenteService: CentroDocenteService;
  let comisionDictaminadoraService: ComisionDictaminadoraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CentroDocenteUpdateComponent],
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
      .overrideTemplate(CentroDocenteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentroDocenteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centroDocenteFormService = TestBed.inject(CentroDocenteFormService);
    centroDocenteService = TestBed.inject(CentroDocenteService);
    comisionDictaminadoraService = TestBed.inject(ComisionDictaminadoraService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ComisionDictaminadora query and add missing value', () => {
      const centroDocente: ICentroDocente = { id: 456 };
      const comisionDictaminadora: IComisionDictaminadora = { id: 62494 };
      centroDocente.comisionDictaminadora = comisionDictaminadora;

      const comisionDictaminadoraCollection: IComisionDictaminadora[] = [{ id: 24320 }];
      jest.spyOn(comisionDictaminadoraService, 'query').mockReturnValue(of(new HttpResponse({ body: comisionDictaminadoraCollection })));
      const additionalComisionDictaminadoras = [comisionDictaminadora];
      const expectedCollection: IComisionDictaminadora[] = [...additionalComisionDictaminadoras, ...comisionDictaminadoraCollection];
      jest.spyOn(comisionDictaminadoraService, 'addComisionDictaminadoraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ centroDocente });
      comp.ngOnInit();

      expect(comisionDictaminadoraService.query).toHaveBeenCalled();
      expect(comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing).toHaveBeenCalledWith(
        comisionDictaminadoraCollection,
        ...additionalComisionDictaminadoras.map(expect.objectContaining)
      );
      expect(comp.comisionDictaminadorasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const centroDocente: ICentroDocente = { id: 456 };
      const comisionDictaminadora: IComisionDictaminadora = { id: 63978 };
      centroDocente.comisionDictaminadora = comisionDictaminadora;

      activatedRoute.data = of({ centroDocente });
      comp.ngOnInit();

      expect(comp.comisionDictaminadorasSharedCollection).toContain(comisionDictaminadora);
      expect(comp.centroDocente).toEqual(centroDocente);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroDocente>>();
      const centroDocente = { id: 123 };
      jest.spyOn(centroDocenteFormService, 'getCentroDocente').mockReturnValue(centroDocente);
      jest.spyOn(centroDocenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroDocente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroDocente }));
      saveSubject.complete();

      // THEN
      expect(centroDocenteFormService.getCentroDocente).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centroDocenteService.update).toHaveBeenCalledWith(expect.objectContaining(centroDocente));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroDocente>>();
      const centroDocente = { id: 123 };
      jest.spyOn(centroDocenteFormService, 'getCentroDocente').mockReturnValue({ id: null });
      jest.spyOn(centroDocenteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroDocente: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroDocente }));
      saveSubject.complete();

      // THEN
      expect(centroDocenteFormService.getCentroDocente).toHaveBeenCalled();
      expect(centroDocenteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroDocente>>();
      const centroDocente = { id: 123 };
      jest.spyOn(centroDocenteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroDocente });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centroDocenteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareComisionDictaminadora', () => {
      it('Should forward to comisionDictaminadoraService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(comisionDictaminadoraService, 'compareComisionDictaminadora');
        comp.compareComisionDictaminadora(entity, entity2);
        expect(comisionDictaminadoraService.compareComisionDictaminadora).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
