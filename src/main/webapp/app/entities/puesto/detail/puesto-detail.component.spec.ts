import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PuestoDetailComponent } from './puesto-detail.component';

describe('Puesto Management Detail Component', () => {
  let comp: PuestoDetailComponent;
  let fixture: ComponentFixture<PuestoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PuestoDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ puesto: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PuestoDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PuestoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load puesto on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.puesto).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
