import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'song',
        loadChildren: () => import('./song/song.module').then(m => m.LyricsSongModule),
      },
      {
        path: 'lyrics',
        loadChildren: () => import('./lyrics/lyrics.module').then(m => m.LyricsLyricsModule),
      },
      {
        path: 'social-user',
        loadChildren: () => import('./social-user/social-user.module').then(m => m.LyricsSocialUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LyricsEntityModule {}
