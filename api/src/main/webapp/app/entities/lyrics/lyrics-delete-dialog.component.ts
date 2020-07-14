import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILyrics } from 'app/shared/model/lyrics.model';
import { LyricsService } from './lyrics.service';

@Component({
  templateUrl: './lyrics-delete-dialog.component.html',
})
export class LyricsDeleteDialogComponent {
  lyrics?: ILyrics;

  constructor(protected lyricsService: LyricsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lyricsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lyricsListModification');
      this.activeModal.close();
    });
  }
}
