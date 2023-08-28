import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IComisionDictaminadora } from '../comision-dictaminadora.model';
import { ComisionDictaminadoraService } from '../service/comision-dictaminadora.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './comision-dictaminadora-delete-dialog.component.html',
})
export class ComisionDictaminadoraDeleteDialogComponent {
  comisionDictaminadora?: IComisionDictaminadora;

  constructor(protected comisionDictaminadoraService: ComisionDictaminadoraService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comisionDictaminadoraService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
