import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LyricsTranslatorPageRoutingModule } from './lyrics-translator-routing.module';

import { LyricsTranslatorPage } from './lyrics-translator.page';

import { QuillModule } from 'ngx-quill';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    QuillModule.forRoot({
      modules: {
        syntax: false,
        toolbar: [['bold', 'italic'],
        [{ 'color': [] }, { 'background': [] }],
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
        [{ 'font': [] }],
        [{ 'align': [] }]]
      }
    }),
    IonicModule,    
    LyricsTranslatorPageRoutingModule
  ],
  declarations: [LyricsTranslatorPage, ]
})
export class LyricsTranslatorPageModule {}
