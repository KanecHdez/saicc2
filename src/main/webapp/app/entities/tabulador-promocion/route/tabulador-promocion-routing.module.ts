import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TabuladorPromocionComponent } from '../list/tabulador-promocion.component';
import { TabuladorPromocionDetailComponent } from '../detail/tabulador-promocion-detail.component';
import { TabuladorPromocionUpdateComponent } from '../update/tabulador-promocion-update.component';
import { TabuladorPromocionRoutingResolveService } from './tabulador-promocion-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const tabuladorPromocionRoute: Routes = [
  {
    path: '',
    component: TabuladorPromocionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TabuladorPromocionDetailComponent,
    resolve: {
      tabuladorPromocion: TabuladorPromocionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TabuladorPromocionUpdateComponent,
    resolve: {
      tabuladorPromocion: TabuladorPromocionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TabuladorPromocionUpdateComponent,
    resolve: {
      tabuladorPromocion: TabuladorPromocionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tabuladorPromocionRoute)],
  exports: [RouterModule],
})
export class TabuladorPromocionRoutingModule {}
