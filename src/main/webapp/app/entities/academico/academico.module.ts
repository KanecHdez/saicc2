import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AcademicoComponent } from './list/academico.component';
import { AcademicoDetailComponent } from './detail/academico-detail.component';
import { AcademicoUpdateComponent } from './update/academico-update.component';
import { AcademicoDeleteDialogComponent } from './delete/academico-delete-dialog.component';
import { AcademicoRoutingModule } from './route/academico-routing.module';

@NgModule({
  imports: [SharedModule, AcademicoRoutingModule],
  declarations: [AcademicoComponent, AcademicoDetailComponent, AcademicoUpdateComponent, AcademicoDeleteDialogComponent],
})
export class AcademicoModule {}
