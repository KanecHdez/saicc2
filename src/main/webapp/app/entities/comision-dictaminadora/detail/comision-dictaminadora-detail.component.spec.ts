import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComisionDictaminadoraDetailComponent } from './comision-dictaminadora-detail.component';

describe('ComisionDictaminadora Management Detail Component', () => {
  let comp: ComisionDictaminadoraDetailComponent;
  let fixture: ComponentFixture<ComisionDictaminadoraDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ComisionDictaminadoraDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ comisionDictaminadora: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ComisionDictaminadoraDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ComisionDictaminadoraDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load comisionDictaminadora on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.comisionDictaminadora).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
