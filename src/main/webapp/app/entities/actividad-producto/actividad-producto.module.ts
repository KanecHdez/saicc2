import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ActividadProductoComponent } from './list/actividad-producto.component';
import { ActividadProductoDetailComponent } from './detail/actividad-producto-detail.component';
import { ActividadProductoUpdateComponent } from './update/actividad-producto-update.component';
import { ActividadProductoDeleteDialogComponent } from './delete/actividad-producto-delete-dialog.component';
import { ActividadProductoRoutingModule } from './route/actividad-producto-routing.module';

@NgModule({
  imports: [SharedModule, ActividadProductoRoutingModule],
  declarations: [
    ActividadProductoComponent,
    ActividadProductoDetailComponent,
    ActividadProductoUpdateComponent,
    ActividadProductoDeleteDialogComponent,
  ],
})
export class ActividadProductoModule {}
