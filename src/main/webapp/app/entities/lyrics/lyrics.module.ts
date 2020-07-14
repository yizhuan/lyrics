import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LyricsSharedModule } from 'app/shared/shared.module';
import { LyricsComponent } from './lyrics.component';
import { LyricsDetailComponent } from './lyrics-detail.component';
import { LyricsUpdateComponent } from './lyrics-update.component';
import { LyricsDeleteDialogComponent } from './lyrics-delete-dialog.component';
import { lyricsRoute } from './lyrics.route';

@NgModule({
  imports: [LyricsSharedModule, RouterModule.forChild(lyricsRoute)],
  declarations: [LyricsComponent, LyricsDetailComponent, LyricsUpdateComponent, LyricsDeleteDialogComponent],
  entryComponents: [LyricsDeleteDialogComponent],
})
export class LyricsLyricsModule {}
