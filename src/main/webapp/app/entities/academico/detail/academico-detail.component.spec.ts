import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AcademicoDetailComponent } from './academico-detail.component';

describe('Academico Management Detail Component', () => {
  let comp: AcademicoDetailComponent;
  let fixture: ComponentFixture<AcademicoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AcademicoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ academico: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AcademicoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AcademicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load academico on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.academico).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
