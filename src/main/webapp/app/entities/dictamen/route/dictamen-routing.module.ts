import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DictamenComponent } from '../list/dictamen.component';
import { DictamenDetailComponent } from '../detail/dictamen-detail.component';
import { DictamenUpdateComponent } from '../update/dictamen-update.component';
import { DictamenRoutingResolveService } from './dictamen-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dictamenRoute: Routes = [
  {
    path: '',
    component: DictamenComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DictamenDetailComponent,
    resolve: {
      dictamen: DictamenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DictamenUpdateComponent,
    resolve: {
      dictamen: DictamenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DictamenUpdateComponent,
    resolve: {
      dictamen: DictamenRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(dictamenRoute)],
  exports: [RouterModule],
})
export class DictamenRoutingModule {}
