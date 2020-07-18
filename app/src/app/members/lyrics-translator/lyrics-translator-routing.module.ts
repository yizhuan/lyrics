import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LyricsTranslatorPage } from './lyrics-translator.page';

const routes: Routes = [
  {
    path: '',
    component: LyricsTranslatorPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LyricsTranslatorPageRoutingModule {}
