import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TabuladorPromocionDetailComponent } from './tabulador-promocion-detail.component';

describe('TabuladorPromocion Management Detail Component', () => {
  let comp: TabuladorPromocionDetailComponent;
  let fixture: ComponentFixture<TabuladorPromocionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TabuladorPromocionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tabuladorPromocion: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TabuladorPromocionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TabuladorPromocionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tabuladorPromocion on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tabuladorPromocion).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
