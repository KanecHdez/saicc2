import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CentroDocenteComponent } from './list/centro-docente.component';
import { CentroDocenteDetailComponent } from './detail/centro-docente-detail.component';
import { CentroDocenteUpdateComponent } from './update/centro-docente-update.component';
import { CentroDocenteDeleteDialogComponent } from './delete/centro-docente-delete-dialog.component';
import { CentroDocenteRoutingModule } from './route/centro-docente-routing.module';

@NgModule({
  imports: [SharedModule, CentroDocenteRoutingModule],
  declarations: [CentroDocenteComponent, CentroDocenteDetailComponent, CentroDocenteUpdateComponent, CentroDocenteDeleteDialogComponent],
})
export class CentroDocenteModule {}
