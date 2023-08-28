import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TabuladorActividadProductoComponent } from './list/tabulador-actividad-producto.component';
import { TabuladorActividadProductoDetailComponent } from './detail/tabulador-actividad-producto-detail.component';
import { TabuladorActividadProductoUpdateComponent } from './update/tabulador-actividad-producto-update.component';
import { TabuladorActividadProductoDeleteDialogComponent } from './delete/tabulador-actividad-producto-delete-dialog.component';
import { TabuladorActividadProductoRoutingModule } from './route/tabulador-actividad-producto-routing.module';

@NgModule({
  imports: [SharedModule, TabuladorActividadProductoRoutingModule],
  declarations: [
    TabuladorActividadProductoComponent,
    TabuladorActividadProductoDetailComponent,
    TabuladorActividadProductoUpdateComponent,
    TabuladorActividadProductoDeleteDialogComponent,
  ],
})
export class TabuladorActividadProductoModule {}
