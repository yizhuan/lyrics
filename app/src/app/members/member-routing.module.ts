import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [   
  {
    path: 'dashboard',
    loadChildren: () => import('./dashboard/dashboard.module').then( m => m.DashboardPageModule)
  },
  {
    path: 'lyrics-translator',
    loadChildren: () => import('./lyrics-translator/lyrics-translator.module').then( m => m.LyricsTranslatorPageModule)
  },
  {
    path: 'song',
    loadChildren: () => import('./song/song.module').then( m => m.SongPageModule ),
  },
  {
    path: 'lyrics',
    loadChildren: () => import('./lyrics/lyrics.module').then( m => m.LyricsPageModule ),
  },  
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class MemberRoutingModule { }
