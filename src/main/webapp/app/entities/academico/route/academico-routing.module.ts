import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AcademicoComponent } from '../list/academico.component';
import { AcademicoDetailComponent } from '../detail/academico-detail.component';
import { AcademicoUpdateComponent } from '../update/academico-update.component';
import { AcademicoRoutingResolveService } from './academico-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const academicoRoute: Routes = [
  {
    path: '',
    component: AcademicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AcademicoDetailComponent,
    resolve: {
      academico: AcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AcademicoUpdateComponent,
    resolve: {
      academico: AcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AcademicoUpdateComponent,
    resolve: {
      academico: AcademicoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(academicoRoute)],
  exports: [RouterModule],
})
export class AcademicoRoutingModule {}
