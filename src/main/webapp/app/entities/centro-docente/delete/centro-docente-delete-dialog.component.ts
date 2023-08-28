import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICentroDocente } from '../centro-docente.model';
import { CentroDocenteService } from '../service/centro-docente.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './centro-docente-delete-dialog.component.html',
})
export class CentroDocenteDeleteDialogComponent {
  centroDocente?: ICentroDocente;

  constructor(protected centroDocenteService: CentroDocenteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centroDocenteService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
