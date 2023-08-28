import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CentroDocenteDetailComponent } from './centro-docente-detail.component';

describe('CentroDocente Management Detail Component', () => {
  let comp: CentroDocenteDetailComponent;
  let fixture: ComponentFixture<CentroDocenteDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CentroDocenteDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ centroDocente: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CentroDocenteDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CentroDocenteDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centroDocente on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.centroDocente).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
