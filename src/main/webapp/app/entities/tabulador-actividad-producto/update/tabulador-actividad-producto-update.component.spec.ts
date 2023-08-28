import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TabuladorActividadProductoFormService } from './tabulador-actividad-producto-form.service';
import { TabuladorActividadProductoService } from '../service/tabulador-actividad-producto.service';
import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';
import { TabuladorPromocionService } from 'app/entities/tabulador-promocion/service/tabulador-promocion.service';

import { TabuladorActividadProductoUpdateComponent } from './tabulador-actividad-producto-update.component';

describe('TabuladorActividadProducto Management Update Component', () => {
  let comp: TabuladorActividadProductoUpdateComponent;
  let fixture: ComponentFixture<TabuladorActividadProductoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tabuladorActividadProductoFormService: TabuladorActividadProductoFormService;
  let tabuladorActividadProductoService: TabuladorActividadProductoService;
  let tabuladorPromocionService: TabuladorPromocionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TabuladorActividadProductoUpdateComponent],
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
      .overrideTemplate(TabuladorActividadProductoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TabuladorActividadProductoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tabuladorActividadProductoFormService = TestBed.inject(TabuladorActividadProductoFormService);
    tabuladorActividadProductoService = TestBed.inject(TabuladorActividadProductoService);
    tabuladorPromocionService = TestBed.inject(TabuladorPromocionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TabuladorActividadProducto query and add missing value', () => {
      const tabuladorActividadProducto: ITabuladorActividadProducto = { id: 456 };
      const tabuladorActSuperior: ITabuladorActividadProducto = { id: 63588 };
      tabuladorActividadProducto.tabuladorActSuperior = tabuladorActSuperior;

      const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [{ id: 46896 }];
      jest
        .spyOn(tabuladorActividadProductoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tabuladorActividadProductoCollection })));
      const additionalTabuladorActividadProductos = [tabuladorActSuperior];
      const expectedCollection: ITabuladorActividadProducto[] = [
        ...additionalTabuladorActividadProductos,
        ...tabuladorActividadProductoCollection,
      ];
      jest
        .spyOn(tabuladorActividadProductoService, 'addTabuladorActividadProductoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tabuladorActividadProducto });
      comp.ngOnInit();

      expect(tabuladorActividadProductoService.query).toHaveBeenCalled();
      expect(tabuladorActividadProductoService.addTabuladorActividadProductoToCollectionIfMissing).toHaveBeenCalledWith(
        tabuladorActividadProductoCollection,
        ...additionalTabuladorActividadProductos.map(expect.objectContaining)
      );
      expect(comp.tabuladorActividadProductosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TabuladorPromocion query and add missing value', () => {
      const tabuladorActividadProducto: ITabuladorActividadProducto = { id: 456 };
      const tabulador: ITabuladorPromocion = { id: 9704 };
      tabuladorActividadProducto.tabulador = tabulador;

      const tabuladorPromocionCollection: ITabuladorPromocion[] = [{ id: 35853 }];
      jest.spyOn(tabuladorPromocionService, 'query').mockReturnValue(of(new HttpResponse({ body: tabuladorPromocionCollection })));
      const additionalTabuladorPromocions = [tabulador];
      const expectedCollection: ITabuladorPromocion[] = [...additionalTabuladorPromocions, ...tabuladorPromocionCollection];
      jest.spyOn(tabuladorPromocionService, 'addTabuladorPromocionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tabuladorActividadProducto });
      comp.ngOnInit();

      expect(tabuladorPromocionService.query).toHaveBeenCalled();
      expect(tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing).toHaveBeenCalledWith(
        tabuladorPromocionCollection,
        ...additionalTabuladorPromocions.map(expect.objectContaining)
      );
      expect(comp.tabuladorPromocionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tabuladorActividadProducto: ITabuladorActividadProducto = { id: 456 };
      const tabuladorActSuperior: ITabuladorActividadProducto = { id: 4195 };
      tabuladorActividadProducto.tabuladorActSuperior = tabuladorActSuperior;
      const tabulador: ITabuladorPromocion = { id: 73076 };
      tabuladorActividadProducto.tabulador = tabulador;

      activatedRoute.data = of({ tabuladorActividadProducto });
      comp.ngOnInit();

      expect(comp.tabuladorActividadProductosSharedCollection).toContain(tabuladorActSuperior);
      expect(comp.tabuladorPromocionsSharedCollection).toContain(tabulador);
      expect(comp.tabuladorActividadProducto).toEqual(tabuladorActividadProducto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorActividadProducto>>();
      const tabuladorActividadProducto = { id: 123 };
      jest.spyOn(tabuladorActividadProductoFormService, 'getTabuladorActividadProducto').mockReturnValue(tabuladorActividadProducto);
      jest.spyOn(tabuladorActividadProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorActividadProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tabuladorActividadProducto }));
      saveSubject.complete();

      // THEN
      expect(tabuladorActividadProductoFormService.getTabuladorActividadProducto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tabuladorActividadProductoService.update).toHaveBeenCalledWith(expect.objectContaining(tabuladorActividadProducto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorActividadProducto>>();
      const tabuladorActividadProducto = { id: 123 };
      jest.spyOn(tabuladorActividadProductoFormService, 'getTabuladorActividadProducto').mockReturnValue({ id: null });
      jest.spyOn(tabuladorActividadProductoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorActividadProducto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tabuladorActividadProducto }));
      saveSubject.complete();

      // THEN
      expect(tabuladorActividadProductoFormService.getTabuladorActividadProducto).toHaveBeenCalled();
      expect(tabuladorActividadProductoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITabuladorActividadProducto>>();
      const tabuladorActividadProducto = { id: 123 };
      jest.spyOn(tabuladorActividadProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tabuladorActividadProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tabuladorActividadProductoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTabuladorActividadProducto', () => {
      it('Should forward to tabuladorActividadProductoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tabuladorActividadProductoService, 'compareTabuladorActividadProducto');
        comp.compareTabuladorActividadProducto(entity, entity2);
        expect(tabuladorActividadProductoService.compareTabuladorActividadProducto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTabuladorPromocion', () => {
      it('Should forward to tabuladorPromocionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tabuladorPromocionService, 'compareTabuladorPromocion');
        comp.compareTabuladorPromocion(entity, entity2);
        expect(tabuladorPromocionService.compareTabuladorPromocion).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
