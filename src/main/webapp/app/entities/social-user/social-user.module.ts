import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LyricsSharedModule } from 'app/shared/shared.module';
import { SocialUserComponent } from './social-user.component';
import { SocialUserDetailComponent } from './social-user-detail.component';
import { SocialUserUpdateComponent } from './social-user-update.component';
import { SocialUserDeleteDialogComponent } from './social-user-delete-dialog.component';
import { socialUserRoute } from './social-user.route';

@NgModule({
  imports: [LyricsSharedModule, RouterModule.forChild(socialUserRoute)],
  declarations: [SocialUserComponent, SocialUserDetailComponent, SocialUserUpdateComponent, SocialUserDeleteDialogComponent],
  entryComponents: [SocialUserDeleteDialogComponent],
})
export class LyricsSocialUserModule {}
