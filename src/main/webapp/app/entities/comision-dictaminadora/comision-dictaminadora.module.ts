import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ComisionDictaminadoraComponent } from './list/comision-dictaminadora.component';
import { ComisionDictaminadoraDetailComponent } from './detail/comision-dictaminadora-detail.component';
import { ComisionDictaminadoraUpdateComponent } from './update/comision-dictaminadora-update.component';
import { ComisionDictaminadoraDeleteDialogComponent } from './delete/comision-dictaminadora-delete-dialog.component';
import { ComisionDictaminadoraRoutingModule } from './route/comision-dictaminadora-routing.module';

@NgModule({
  imports: [SharedModule, ComisionDictaminadoraRoutingModule],
  declarations: [
    ComisionDictaminadoraComponent,
    ComisionDictaminadoraDetailComponent,
    ComisionDictaminadoraUpdateComponent,
    ComisionDictaminadoraDeleteDialogComponent,
  ],
})
export class ComisionDictaminadoraModule {}
