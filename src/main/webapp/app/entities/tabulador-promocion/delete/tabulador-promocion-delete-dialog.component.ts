import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITabuladorPromocion } from '../tabulador-promocion.model';
import { TabuladorPromocionService } from '../service/tabulador-promocion.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tabulador-promocion-delete-dialog.component.html',
})
export class TabuladorPromocionDeleteDialogComponent {
  tabuladorPromocion?: ITabuladorPromocion;

  constructor(protected tabuladorPromocionService: TabuladorPromocionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tabuladorPromocionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
