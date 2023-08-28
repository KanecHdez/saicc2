import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ActividadProductoFormService } from './actividad-producto-form.service';
import { ActividadProductoService } from '../service/actividad-producto.service';
import { IActividadProducto } from '../actividad-producto.model';
import { ITabuladorActividadProducto } from 'app/entities/tabulador-actividad-producto/tabulador-actividad-producto.model';
import { TabuladorActividadProductoService } from 'app/entities/tabulador-actividad-producto/service/tabulador-actividad-producto.service';
import { IDictamen } from 'app/entities/dictamen/dictamen.model';
import { DictamenService } from 'app/entities/dictamen/service/dictamen.service';

import { ActividadProductoUpdateComponent } from './actividad-producto-update.component';

describe('ActividadProducto Management Update Component', () => {
  let comp: ActividadProductoUpdateComponent;
  let fixture: ComponentFixture<ActividadProductoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let actividadProductoFormService: ActividadProductoFormService;
  let actividadProductoService: ActividadProductoService;
  let tabuladorActividadProductoService: TabuladorActividadProductoService;
  let dictamenService: DictamenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ActividadProductoUpdateComponent],
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
      .overrideTemplate(ActividadProductoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ActividadProductoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    actividadProductoFormService = TestBed.inject(ActividadProductoFormService);
    actividadProductoService = TestBed.inject(ActividadProductoService);
    tabuladorActividadProductoService = TestBed.inject(TabuladorActividadProductoService);
    dictamenService = TestBed.inject(DictamenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TabuladorActividadProducto query and add missing value', () => {
      const actividadProducto: IActividadProducto = { id: 456 };
      const tabuladorActProd: ITabuladorActividadProducto = { id: 34380 };
      actividadProducto.tabuladorActProd = tabuladorActProd;

      const tabuladorActividadProductoCollection: ITabuladorActividadProducto[] = [{ id: 59093 }];
      jest
        .spyOn(tabuladorActividadProductoService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: tabuladorActividadProductoCollection })));
      const additionalTabuladorActividadProductos = [tabuladorActProd];
      const expectedCollection: ITabuladorActividadProducto[] = [
        ...additionalTabuladorActividadProductos,
        ...tabuladorActividadProductoCollection,
      ];
      jest
        .spyOn(tabuladorActividadProductoService, 'addTabuladorActividadProductoToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ actividadProducto });
      comp.ngOnInit();

      expect(tabuladorActividadProductoService.query).toHaveBeenCalled();
      expect(tabuladorActividadProductoService.addTabuladorActividadProductoToCollectionIfMissing).toHaveBeenCalledWith(
        tabuladorActividadProductoCollection,
        ...additionalTabuladorActividadProductos.map(expect.objectContaining)
      );
      expect(comp.tabuladorActividadProductosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Dictamen query and add missing value', () => {
      const actividadProducto: IActividadProducto = { id: 456 };
      const dictamen: IDictamen = { id: 63427 };
      actividadProducto.dictamen = dictamen;

      const dictamenCollection: IDictamen[] = [{ id: 45562 }];
      jest.spyOn(dictamenService, 'query').mockReturnValue(of(new HttpResponse({ body: dictamenCollection })));
      const additionalDictamen = [dictamen];
      const expectedCollection: IDictamen[] = [...additionalDictamen, ...dictamenCollection];
      jest.spyOn(dictamenService, 'addDictamenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ actividadProducto });
      comp.ngOnInit();

      expect(dictamenService.query).toHaveBeenCalled();
      expect(dictamenService.addDictamenToCollectionIfMissing).toHaveBeenCalledWith(
        dictamenCollection,
        ...additionalDictamen.map(expect.objectContaining)
      );
      expect(comp.dictamenSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const actividadProducto: IActividadProducto = { id: 456 };
      const tabuladorActProd: ITabuladorActividadProducto = { id: 46744 };
      actividadProducto.tabuladorActProd = tabuladorActProd;
      const dictamen: IDictamen = { id: 98397 };
      actividadProducto.dictamen = dictamen;

      activatedRoute.data = of({ actividadProducto });
      comp.ngOnInit();

      expect(comp.tabuladorActividadProductosSharedCollection).toContain(tabuladorActProd);
      expect(comp.dictamenSharedCollection).toContain(dictamen);
      expect(comp.actividadProducto).toEqual(actividadProducto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActividadProducto>>();
      const actividadProducto = { id: 123 };
      jest.spyOn(actividadProductoFormService, 'getActividadProducto').mockReturnValue(actividadProducto);
      jest.spyOn(actividadProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actividadProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: actividadProducto }));
      saveSubject.complete();

      // THEN
      expect(actividadProductoFormService.getActividadProducto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(actividadProductoService.update).toHaveBeenCalledWith(expect.objectContaining(actividadProducto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActividadProducto>>();
      const actividadProducto = { id: 123 };
      jest.spyOn(actividadProductoFormService, 'getActividadProducto').mockReturnValue({ id: null });
      jest.spyOn(actividadProductoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actividadProducto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: actividadProducto }));
      saveSubject.complete();

      // THEN
      expect(actividadProductoFormService.getActividadProducto).toHaveBeenCalled();
      expect(actividadProductoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IActividadProducto>>();
      const actividadProducto = { id: 123 };
      jest.spyOn(actividadProductoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ actividadProducto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(actividadProductoService.update).toHaveBeenCalled();
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

    describe('compareDictamen', () => {
      it('Should forward to dictamenService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(dictamenService, 'compareDictamen');
        comp.compareDictamen(entity, entity2);
        expect(dictamenService.compareDictamen).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
