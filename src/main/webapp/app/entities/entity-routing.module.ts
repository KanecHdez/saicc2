import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'academico',
        data: { pageTitle: 'saiccApp.academico.home.title' },
        loadChildren: () => import('./academico/academico.module').then(m => m.AcademicoModule),
      },
      {
        path: 'dictamen',
        data: { pageTitle: 'saiccApp.dictamen.home.title' },
        loadChildren: () => import('./dictamen/dictamen.module').then(m => m.DictamenModule),
      },
      {
        path: 'periodo',
        data: { pageTitle: 'saiccApp.periodo.home.title' },
        loadChildren: () => import('./periodo/periodo.module').then(m => m.PeriodoModule),
      },
      {
        path: 'comision-dictaminadora',
        data: { pageTitle: 'saiccApp.comisionDictaminadora.home.title' },
        loadChildren: () => import('./comision-dictaminadora/comision-dictaminadora.module').then(m => m.ComisionDictaminadoraModule),
      },
      {
        path: 'centro-docente',
        data: { pageTitle: 'saiccApp.centroDocente.home.title' },
        loadChildren: () => import('./centro-docente/centro-docente.module').then(m => m.CentroDocenteModule),
      },
      {
        path: 'puesto',
        data: { pageTitle: 'saiccApp.puesto.home.title' },
        loadChildren: () => import('./puesto/puesto.module').then(m => m.PuestoModule),
      },
      {
        path: 'tabulador-promocion',
        data: { pageTitle: 'saiccApp.tabuladorPromocion.home.title' },
        loadChildren: () => import('./tabulador-promocion/tabulador-promocion.module').then(m => m.TabuladorPromocionModule),
      },
      {
        path: 'tabulador-actividad-producto',
        data: { pageTitle: 'saiccApp.tabuladorActividadProducto.home.title' },
        loadChildren: () =>
          import('./tabulador-actividad-producto/tabulador-actividad-producto.module').then(m => m.TabuladorActividadProductoModule),
      },
      {
        path: 'actividad-producto',
        data: { pageTitle: 'saiccApp.actividadProducto.home.title' },
        loadChildren: () => import('./actividad-producto/actividad-producto.module').then(m => m.ActividadProductoModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
