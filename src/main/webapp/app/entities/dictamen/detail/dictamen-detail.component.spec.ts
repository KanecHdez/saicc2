import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DictamenDetailComponent } from './dictamen-detail.component';

describe('Dictamen Management Detail Component', () => {
  let comp: DictamenDetailComponent;
  let fixture: ComponentFixture<DictamenDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DictamenDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dictamen: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DictamenDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DictamenDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dictamen on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dictamen).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
