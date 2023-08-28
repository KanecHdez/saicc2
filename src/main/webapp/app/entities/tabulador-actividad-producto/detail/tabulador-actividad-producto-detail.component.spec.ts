import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TabuladorActividadProductoDetailComponent } from './tabulador-actividad-producto-detail.component';

describe('TabuladorActividadProducto Management Detail Component', () => {
  let comp: TabuladorActividadProductoDetailComponent;
  let fixture: ComponentFixture<TabuladorActividadProductoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabuladorActividadProductoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tabuladorActividadProducto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TabuladorActividadProductoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TabuladorActividadProductoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tabuladorActividadProducto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tabuladorActividadProducto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
