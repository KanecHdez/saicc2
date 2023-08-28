import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PeriodoDetailComponent } from './periodo-detail.component';

describe('Periodo Management Detail Component', () => {
  let comp: PeriodoDetailComponent;
  let fixture: ComponentFixture<PeriodoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PeriodoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ periodo: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PeriodoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PeriodoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load periodo on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.periodo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
