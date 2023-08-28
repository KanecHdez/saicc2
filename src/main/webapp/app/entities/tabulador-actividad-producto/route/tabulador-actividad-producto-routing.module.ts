import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TabuladorActividadProductoComponent } from '../list/tabulador-actividad-producto.component';
import { TabuladorActividadProductoDetailComponent } from '../detail/tabulador-actividad-producto-detail.component';
import { TabuladorActividadProductoUpdateComponent } from '../update/tabulador-actividad-producto-update.component';
import { TabuladorActividadProductoRoutingResolveService } from './tabulador-actividad-producto-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tabuladorActividadProductoRoute: Routes = [
  {
    path: '',
    component: TabuladorActividadProductoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TabuladorActividadProductoDetailComponent,
    resolve: {
      tabuladorActividadProducto: TabuladorActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TabuladorActividadProductoUpdateComponent,
    resolve: {
      tabuladorActividadProducto: TabuladorActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TabuladorActividadProductoUpdateComponent,
    resolve: {
      tabuladorActividadProducto: TabuladorActividadProductoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tabuladorActividadProductoRoute)],
  exports: [RouterModule],
})
export class TabuladorActividadProductoRoutingModule {}
