import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodo } from '../periodo.model';
import { PeriodoService } from '../service/periodo.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './periodo-delete-dialog.component.html',
})
export class PeriodoDeleteDialogComponent {
  periodo?: IPeriodo;

  constructor(protected periodoService: PeriodoService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.periodoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
