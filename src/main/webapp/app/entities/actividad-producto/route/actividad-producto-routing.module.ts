import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ActividadProductoComponent } from '../list/actividad-producto.component';
import { ActividadProductoDetailComponent } from '../detail/actividad-producto-detail.component';
import { ActividadProductoUpdateComponent } from '../update/actividad-producto-update.component';
import { ActividadProductoRoutingResolveService } from './actividad-producto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const actividadProductoRoute: Routes = [
  {
    path: '',
    component: ActividadProductoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActividadProductoDetailComponent,
    resolve: {
      actividadProducto: ActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActividadProductoUpdateComponent,
    resolve: {
      actividadProducto: ActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActividadProductoUpdateComponent,
    resolve: {
      actividadProducto: ActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(actividadProductoRoute)],
  exports: [RouterModule],
})
export class ActividadProductoRoutingModule {}
