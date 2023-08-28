import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';

import { ComisionDictaminadoraComponent } from './comision-dictaminadora.component';

describe('ComisionDictaminadora Management Component', () => {
  let comp: ComisionDictaminadoraComponent;
  let fixture: ComponentFixture<ComisionDictaminadoraComponent>;
  let service: ComisionDictaminadoraService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'comision-dictaminadora', component: ComisionDictaminadoraComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ComisionDictaminadoraComponent],
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
      .overrideTemplate(ComisionDictaminadoraComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComisionDictaminadoraComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ComisionDictaminadoraService);

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
    expect(comp.comisionDictaminadoras?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to comisionDictaminadoraService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getComisionDictaminadoraIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getComisionDictaminadoraIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
