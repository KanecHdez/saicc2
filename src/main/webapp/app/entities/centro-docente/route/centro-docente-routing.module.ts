import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CentroDocenteComponent } from '../list/centro-docente.component';
import { CentroDocenteDetailComponent } from '../detail/centro-docente-detail.component';
import { CentroDocenteUpdateComponent } from '../update/centro-docente-update.component';
import { CentroDocenteRoutingResolveService } from './centro-docente-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const centroDocenteRoute: Routes = [
  {
    path: '',
    component: CentroDocenteComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CentroDocenteDetailComponent,
    resolve: {
      centroDocente: CentroDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CentroDocenteUpdateComponent,
    resolve: {
      centroDocente: CentroDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CentroDocenteUpdateComponent,
    resolve: {
      centroDocente: CentroDocenteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(centroDocenteRoute)],
  exports: [RouterModule],
})
export class CentroDocenteRoutingModule {}
