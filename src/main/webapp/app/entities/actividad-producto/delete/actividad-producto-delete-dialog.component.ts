import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IActividadProducto } from '../actividad-producto.model';
import { ActividadProductoService } from '../service/actividad-producto.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './actividad-producto-delete-dialog.component.html',
})
export class ActividadProductoDeleteDialogComponent {
  actividadProducto?: IActividadProducto;

  constructor(protected actividadProductoService: ActividadProductoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actividadProductoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
