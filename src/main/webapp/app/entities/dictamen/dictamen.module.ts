import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DictamenComponent } from './list/dictamen.component';
import { DictamenDetailComponent } from './detail/dictamen-detail.component';
import { DictamenUpdateComponent } from './update/dictamen-update.component';
import { DictamenDeleteDialogComponent } from './delete/dictamen-delete-dialog.component';
import { DictamenRoutingModule } from './route/dictamen-routing.module';

@NgModule({
  imports: [SharedModule, DictamenRoutingModule],
  declarations: [DictamenComponent, DictamenDetailComponent, DictamenUpdateComponent, DictamenDeleteDialogComponent],
})
export class DictamenModule {}
