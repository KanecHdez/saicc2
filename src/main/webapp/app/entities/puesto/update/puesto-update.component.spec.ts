import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PuestoFormService } from './puesto-form.service';
import { PuestoService } from '../service/puesto.service';
import { IPuesto } from '../puesto.model';

import { PuestoUpdateComponent } from './puesto-update.component';

describe('Puesto Management Update Component', () => {
  let comp: PuestoUpdateComponent;
  let fixture: ComponentFixture<PuestoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let puestoFormService: PuestoFormService;
  let puestoService: PuestoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PuestoUpdateComponent],
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
      .overrideTemplate(PuestoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PuestoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    puestoFormService = TestBed.inject(PuestoFormService);
    puestoService = TestBed.inject(PuestoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const puesto: IPuesto = { id: 456 };

      activatedRoute.data = of({ puesto });
      comp.ngOnInit();

      expect(comp.puesto).toEqual(puesto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPuesto>>();
      const puesto = { id: 123 };
      jest.spyOn(puestoFormService, 'getPuesto').mockReturnValue(puesto);
      jest.spyOn(puestoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ puesto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: puesto }));
      saveSubject.complete();

      // THEN
      expect(puestoFormService.getPuesto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(puestoService.update).toHaveBeenCalledWith(expect.objectContaining(puesto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPuesto>>();
      const puesto = { id: 123 };
      jest.spyOn(puestoFormService, 'getPuesto').mockReturnValue({ id: null });
      jest.spyOn(puestoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ puesto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: puesto }));
      saveSubject.complete();

      // THEN
      expect(puestoFormService.getPuesto).toHaveBeenCalled();
      expect(puestoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPuesto>>();
      const puesto = { id: 123 };
      jest.spyOn(puestoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ puesto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(puestoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
