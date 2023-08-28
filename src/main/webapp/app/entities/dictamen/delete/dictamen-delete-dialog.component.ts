import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDictamen } from '../dictamen.model';
import { DictamenService } from '../service/dictamen.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './dictamen-delete-dialog.component.html',
})
export class DictamenDeleteDialogComponent {
  dictamen?: IDictamen;

  constructor(protected dictamenService: DictamenService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dictamenService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
