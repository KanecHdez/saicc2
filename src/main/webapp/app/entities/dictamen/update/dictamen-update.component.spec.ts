import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DictamenFormService } from './dictamen-form.service';
import { DictamenService } from '../service/dictamen.service';
import { IDictamen } from '../dictamen.model';
import { IAcademico } from 'app/entities/academico/academico.model';
import { AcademicoService } from 'app/entities/academico/service/academico.service';
import { IPuesto } from 'app/entities/puesto/puesto.model';
import { PuestoService } from 'app/entities/puesto/service/puesto.service';
import { IPeriodo } from 'app/entities/periodo/periodo.model';
import { PeriodoService } from 'app/entities/periodo/service/periodo.service';
import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';
import { ComisionDictaminadoraService } from 'app/entities/comision-dictaminadora/service/comision-dictaminadora.service';
import { ICentroDocente } from 'app/entities/centro-docente/centro-docente.model';
import { CentroDocenteService } from 'app/entities/centro-docente/service/centro-docente.service';
import { ITabuladorPromocion } from 'app/entities/tabulador-promocion/tabulador-promocion.model';
import { TabuladorPromocionService } from 'app/entities/tabulador-promocion/service/tabulador-promocion.service';

import { DictamenUpdateComponent } from './dictamen-update.component';

describe('Dictamen Management Update Component', () => {
  let comp: DictamenUpdateComponent;
  let fixture: ComponentFixture<DictamenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dictamenFormService: DictamenFormService;
  let dictamenService: DictamenService;
  let academicoService: AcademicoService;
  let puestoService: PuestoService;
  let periodoService: PeriodoService;
  let comisionDictaminadoraService: ComisionDictaminadoraService;
  let centroDocenteService: CentroDocenteService;
  let tabuladorPromocionService: TabuladorPromocionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DictamenUpdateComponent],
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
      .overrideTemplate(DictamenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DictamenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dictamenFormService = TestBed.inject(DictamenFormService);
    dictamenService = TestBed.inject(DictamenService);
    academicoService = TestBed.inject(AcademicoService);
    puestoService = TestBed.inject(PuestoService);
    periodoService = TestBed.inject(PeriodoService);
    comisionDictaminadoraService = TestBed.inject(ComisionDictaminadoraService);
    centroDocenteService = TestBed.inject(CentroDocenteService);
    tabuladorPromocionService = TestBed.inject(TabuladorPromocionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Academico query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const academico: IAcademico = { id: 6424 };
      dictamen.academico = academico;

      const academicoCollection: IAcademico[] = [{ id: 42361 }];
      jest.spyOn(academicoService, 'query').mockReturnValue(of(new HttpResponse({ body: academicoCollection })));
      const additionalAcademicos = [academico];
      const expectedCollection: IAcademico[] = [...additionalAcademicos, ...academicoCollection];
      jest.spyOn(academicoService, 'addAcademicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(academicoService.query).toHaveBeenCalled();
      expect(academicoService.addAcademicoToCollectionIfMissing).toHaveBeenCalledWith(
        academicoCollection,
        ...additionalAcademicos.map(expect.objectContaining)
      );
      expect(comp.academicosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Puesto query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const puestoActual: IPuesto = { id: 2730 };
      dictamen.puestoActual = puestoActual;
      const puestoSolicitado: IPuesto = { id: 70355 };
      dictamen.puestoSolicitado = puestoSolicitado;

      const puestoCollection: IPuesto[] = [{ id: 46874 }];
      jest.spyOn(puestoService, 'query').mockReturnValue(of(new HttpResponse({ body: puestoCollection })));
      const additionalPuestos = [puestoActual, puestoSolicitado];
      const expectedCollection: IPuesto[] = [...additionalPuestos, ...puestoCollection];
      jest.spyOn(puestoService, 'addPuestoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(puestoService.query).toHaveBeenCalled();
      expect(puestoService.addPuestoToCollectionIfMissing).toHaveBeenCalledWith(
        puestoCollection,
        ...additionalPuestos.map(expect.objectContaining)
      );
      expect(comp.puestosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Periodo query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const periodo: IPeriodo = { id: 17307 };
      dictamen.periodo = periodo;

      const periodoCollection: IPeriodo[] = [{ id: 76094 }];
      jest.spyOn(periodoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoCollection })));
      const additionalPeriodos = [periodo];
      const expectedCollection: IPeriodo[] = [...additionalPeriodos, ...periodoCollection];
      jest.spyOn(periodoService, 'addPeriodoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(periodoService.query).toHaveBeenCalled();
      expect(periodoService.addPeriodoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoCollection,
        ...additionalPeriodos.map(expect.objectContaining)
      );
      expect(comp.periodosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ComisionDictaminadora query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const comisionDictaminadora: IComisionDictaminadora = { id: 98437 };
      dictamen.comisionDictaminadora = comisionDictaminadora;

      const comisionDictaminadoraCollection: IComisionDictaminadora[] = [{ id: 29076 }];
      jest.spyOn(comisionDictaminadoraService, 'query').mockReturnValue(of(new HttpResponse({ body: comisionDictaminadoraCollection })));
      const additionalComisionDictaminadoras = [comisionDictaminadora];
      const expectedCollection: IComisionDictaminadora[] = [...additionalComisionDictaminadoras, ...comisionDictaminadoraCollection];
      jest.spyOn(comisionDictaminadoraService, 'addComisionDictaminadoraToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(comisionDictaminadoraService.query).toHaveBeenCalled();
      expect(comisionDictaminadoraService.addComisionDictaminadoraToCollectionIfMissing).toHaveBeenCalledWith(
        comisionDictaminadoraCollection,
        ...additionalComisionDictaminadoras.map(expect.objectContaining)
      );
      expect(comp.comisionDictaminadorasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CentroDocente query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const dependencia: ICentroDocente = { id: 65376 };
      dictamen.dependencia = dependencia;

      const centroDocenteCollection: ICentroDocente[] = [{ id: 27854 }];
      jest.spyOn(centroDocenteService, 'query').mockReturnValue(of(new HttpResponse({ body: centroDocenteCollection })));
      const additionalCentroDocentes = [dependencia];
      const expectedCollection: ICentroDocente[] = [...additionalCentroDocentes, ...centroDocenteCollection];
      jest.spyOn(centroDocenteService, 'addCentroDocenteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(centroDocenteService.query).toHaveBeenCalled();
      expect(centroDocenteService.addCentroDocenteToCollectionIfMissing).toHaveBeenCalledWith(
        centroDocenteCollection,
        ...additionalCentroDocentes.map(expect.objectContaining)
      );
      expect(comp.centroDocentesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TabuladorPromocion query and add missing value', () => {
      const dictamen: IDictamen = { id: 456 };
      const tabuladorPromocion: ITabuladorPromocion = { id: 38506 };
      dictamen.tabuladorPromocion = tabuladorPromocion;

      const tabuladorPromocionCollection: ITabuladorPromocion[] = [{ id: 65913 }];
      jest.spyOn(tabuladorPromocionService, 'query').mockReturnValue(of(new HttpResponse({ body: tabuladorPromocionCollection })));
      const additionalTabuladorPromocions = [tabuladorPromocion];
      const expectedCollection: ITabuladorPromocion[] = [...additionalTabuladorPromocions, ...tabuladorPromocionCollection];
      jest.spyOn(tabuladorPromocionService, 'addTabuladorPromocionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(tabuladorPromocionService.query).toHaveBeenCalled();
      expect(tabuladorPromocionService.addTabuladorPromocionToCollectionIfMissing).toHaveBeenCalledWith(
        tabuladorPromocionCollection,
        ...additionalTabuladorPromocions.map(expect.objectContaining)
      );
      expect(comp.tabuladorPromocionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dictamen: IDictamen = { id: 456 };
      const academico: IAcademico = { id: 58502 };
      dictamen.academico = academico;
      const puestoActual: IPuesto = { id: 54499 };
      dictamen.puestoActual = puestoActual;
      const puestoSolicitado: IPuesto = { id: 58606 };
      dictamen.puestoSolicitado = puestoSolicitado;
      const periodo: IPeriodo = { id: 9674 };
      dictamen.periodo = periodo;
      const comisionDictaminadora: IComisionDictaminadora = { id: 59435 };
      dictamen.comisionDictaminadora = comisionDictaminadora;
      const dependencia: ICentroDocente = { id: 68559 };
      dictamen.dependencia = dependencia;
      const tabuladorPromocion: ITabuladorPromocion = { id: 51371 };
      dictamen.tabuladorPromocion = tabuladorPromocion;

      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      expect(comp.academicosSharedCollection).toContain(academico);
      expect(comp.puestosSharedCollection).toContain(puestoActual);
      expect(comp.puestosSharedCollection).toContain(puestoSolicitado);
      expect(comp.periodosSharedCollection).toContain(periodo);
      expect(comp.comisionDictaminadorasSharedCollection).toContain(comisionDictaminadora);
      expect(comp.centroDocentesSharedCollection).toContain(dependencia);
      expect(comp.tabuladorPromocionsSharedCollection).toContain(tabuladorPromocion);
      expect(comp.dictamen).toEqual(dictamen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDictamen>>();
      const dictamen = { id: 123 };
      jest.spyOn(dictamenFormService, 'getDictamen').mockReturnValue(dictamen);
      jest.spyOn(dictamenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dictamen }));
      saveSubject.complete();

      // THEN
      expect(dictamenFormService.getDictamen).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dictamenService.update).toHaveBeenCalledWith(expect.objectContaining(dictamen));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDictamen>>();
      const dictamen = { id: 123 };
      jest.spyOn(dictamenFormService, 'getDictamen').mockReturnValue({ id: null });
      jest.spyOn(dictamenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dictamen: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dictamen }));
      saveSubject.complete();

      // THEN
      expect(dictamenFormService.getDictamen).toHaveBeenCalled();
      expect(dictamenService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDictamen>>();
      const dictamen = { id: 123 };
      jest.spyOn(dictamenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dictamen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dictamenService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAcademico', () => {
      it('Should forward to academicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(academicoService, 'compareAcademico');
        comp.compareAcademico(entity, entity2);
        expect(academicoService.compareAcademico).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePuesto', () => {
      it('Should forward to puestoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(puestoService, 'comparePuesto');
        comp.comparePuesto(entity, entity2);
        expect(puestoService.comparePuesto).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePeriodo', () => {
      it('Should forward to periodoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoService, 'comparePeriodo');
        comp.comparePeriodo(entity, entity2);
        expect(periodoService.comparePeriodo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareComisionDictaminadora', () => {
      it('Should forward to comisionDictaminadoraService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(comisionDictaminadoraService, 'compareComisionDictaminadora');
        comp.compareComisionDictaminadora(entity, entity2);
        expect(comisionDictaminadoraService.compareComisionDictaminadora).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCentroDocente', () => {
      it('Should forward to centroDocenteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroDocenteService, 'compareCentroDocente');
        comp.compareCentroDocente(entity, entity2);
        expect(centroDocenteService.compareCentroDocente).toHaveBeenCalledWith(entity, entity2);
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
