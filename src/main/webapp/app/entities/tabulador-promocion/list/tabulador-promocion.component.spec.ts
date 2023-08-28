import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TabuladorPromocionService } from '../service/tabulador-promocion.service';

import { TabuladorPromocionComponent } from './tabulador-promocion.component';

describe('TabuladorPromocion Management Component', () => {
  let comp: TabuladorPromocionComponent;
  let fixture: ComponentFixture<TabuladorPromocionComponent>;
  let service: TabuladorPromocionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'tabulador-promocion', component: TabuladorPromocionComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [TabuladorPromocionComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(TabuladorPromocionComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TabuladorPromocionComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TabuladorPromocionService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tabuladorPromocions?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to tabuladorPromocionService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTabuladorPromocionIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTabuladorPromocionIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
