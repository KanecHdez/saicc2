import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PuestoComponent } from './list/puesto.component';
import { PuestoDetailComponent } from './detail/puesto-detail.component';
import { PuestoUpdateComponent } from './update/puesto-update.component';
import { PuestoDeleteDialogComponent } from './delete/puesto-delete-dialog.component';
import { PuestoRoutingModule } from './route/puesto-routing.module';

@NgModule({
  imports: [SharedModule, PuestoRoutingModule],
  declarations: [PuestoComponent, PuestoDetailComponent, PuestoUpdateComponent, PuestoDeleteDialogComponent],
})
export class PuestoModule {}
