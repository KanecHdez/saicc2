import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITabuladorActividadProducto } from '../tabulador-actividad-producto.model';
import { TabuladorActividadProductoService } from '../service/tabulador-actividad-producto.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './tabulador-actividad-producto-delete-dialog.component.html',
})
export class TabuladorActividadProductoDeleteDialogComponent {
  tabuladorActividadProducto?: ITabuladorActividadProducto;

  constructor(protected tabuladorActividadProductoService: TabuladorActividadProductoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tabuladorActividadProductoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
