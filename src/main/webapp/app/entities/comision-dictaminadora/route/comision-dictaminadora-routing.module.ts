import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ComisionDictaminadoraComponent } from '../list/comision-dictaminadora.component';
import { ComisionDictaminadoraDetailComponent } from '../detail/comision-dictaminadora-detail.component';
import { ComisionDictaminadoraUpdateComponent } from '../update/comision-dictaminadora-update.component';
import { ComisionDictaminadoraRoutingResolveService } from './comision-dictaminadora-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const comisionDictaminadoraRoute: Routes = [
  {
    path: '',
    component: ComisionDictaminadoraComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComisionDictaminadoraDetailComponent,
    resolve: {
      comisionDictaminadora: ComisionDictaminadoraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComisionDictaminadoraUpdateComponent,
    resolve: {
      comisionDictaminadora: ComisionDictaminadoraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComisionDictaminadoraUpdateComponent,
    resolve: {
      comisionDictaminadora: ComisionDictaminadoraRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(comisionDictaminadoraRoute)],
  exports: [RouterModule],
})
export class ComisionDictaminadoraRoutingModule {}
