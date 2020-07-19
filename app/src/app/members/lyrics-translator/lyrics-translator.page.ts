import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-lyrics-translator',
  templateUrl: './lyrics-translator.page.html',
  styleUrls: ['./lyrics-translator.page.scss'],
})
export class LyricsTranslatorPage implements OnInit {

  lyricsTranslation: string;

  constructor() { }

  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    console.log(form);

    this.lyricsTranslation = form.value.htmlContent;

    console.log(this.lyricsTranslation);
  }

}
