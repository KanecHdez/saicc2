import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ActividadProductoDetailComponent } from './actividad-producto-detail.component';

describe('ActividadProducto Management Detail Component', () => {
  let comp: ActividadProductoDetailComponent;
  let fixture: ComponentFixture<ActividadProductoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActividadProductoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ actividadProducto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ActividadProductoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ActividadProductoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load actividadProducto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.actividadProducto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
