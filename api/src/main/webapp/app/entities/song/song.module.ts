import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LyricsSharedModule } from 'app/shared/shared.module';
import { SongComponent } from './song.component';
import { SongDetailComponent } from './song-detail.component';
import { SongUpdateComponent } from './song-update.component';
import { SongDeleteDialogComponent } from './song-delete-dialog.component';
import { songRoute } from './song.route';

@NgModule({
  imports: [LyricsSharedModule, RouterModule.forChild(songRoute)],
  declarations: [SongComponent, SongDetailComponent, SongUpdateComponent, SongDeleteDialogComponent],
  entryComponents: [SongDeleteDialogComponent],
})
export class LyricsSongModule {}
