import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PuestoComponent } from '../list/puesto.component';
import { PuestoDetailComponent } from '../detail/puesto-detail.component';
import { PuestoUpdateComponent } from '../update/puesto-update.component';
import { PuestoRoutingResolveService } from './puesto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const puestoRoute: Routes = [
  {
    path: '',
    component: PuestoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PuestoDetailComponent,
    resolve: {
      puesto: PuestoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PuestoUpdateComponent,
    resolve: {
      puesto: PuestoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PuestoUpdateComponent,
    resolve: {
      puesto: PuestoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(puestoRoute)],
  exports: [RouterModule],
})
export class PuestoRoutingModule {}
