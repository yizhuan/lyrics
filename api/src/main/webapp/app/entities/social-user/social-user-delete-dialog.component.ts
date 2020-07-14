import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISocialUser } from 'app/shared/model/social-user.model';
import { SocialUserService } from './social-user.service';

@Component({
  templateUrl: './social-user-delete-dialog.component.html',
})
export class SocialUserDeleteDialogComponent {
  socialUser?: ISocialUser;

  constructor(
    protected socialUserService: SocialUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.socialUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('socialUserListModification');
      this.activeModal.close();
    });
  }
}
