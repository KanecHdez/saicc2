import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PeriodoComponent } from '../list/periodo.component';
import { PeriodoDetailComponent } from '../detail/periodo-detail.component';
import { PeriodoUpdateComponent } from '../update/periodo-update.component';
import { PeriodoRoutingResolveService } from './periodo-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const periodoRoute: Routes = [
  {
    path: '',
    component: PeriodoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PeriodoDetailComponent,
    resolve: {
      periodo: PeriodoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PeriodoUpdateComponent,
    resolve: {
      periodo: PeriodoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PeriodoUpdateComponent,
    resolve: {
      periodo: PeriodoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(periodoRoute)],
  exports: [RouterModule],
})
export class PeriodoRoutingModule {}
