import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TabuladorPromocionComponent } from './list/tabulador-promocion.component';
import { TabuladorPromocionDetailComponent } from './detail/tabulador-promocion-detail.component';
import { TabuladorPromocionUpdateComponent } from './update/tabulador-promocion-update.component';
import { TabuladorPromocionDeleteDialogComponent } from './delete/tabulador-promocion-delete-dialog.component';
import { TabuladorPromocionRoutingModule } from './route/tabulador-promocion-routing.module';

@NgModule({
  imports: [SharedModule, TabuladorPromocionRoutingModule],
  declarations: [
    TabuladorPromocionComponent,
    TabuladorPromocionDetailComponent,
    TabuladorPromocionUpdateComponent,
    TabuladorPromocionDeleteDialogComponent,
  ],
})
export class TabuladorPromocionModule {}
