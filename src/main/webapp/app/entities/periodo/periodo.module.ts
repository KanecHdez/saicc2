import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PeriodoComponent } from './list/periodo.component';
import { PeriodoDetailComponent } from './detail/periodo-detail.component';
import { PeriodoUpdateComponent } from './update/periodo-update.component';
import { PeriodoDeleteDialogComponent } from './delete/periodo-delete-dialog.component';
import { PeriodoRoutingModule } from './route/periodo-routing.module';

@NgModule({
  imports: [SharedModule, PeriodoRoutingModule],
  declarations: [PeriodoComponent, PeriodoDetailComponent, PeriodoUpdateComponent, PeriodoDeleteDialogComponent],
})
export class PeriodoModule {}
