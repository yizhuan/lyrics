import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Lyrics } from './lyrics.model';
import { LyricsService } from './lyrics.service';

@Component({
  selector: 'page-lyrics-update',
  templateUrl: 'lyrics-update.html',
})
export class LyricsUpdatePage implements OnInit {
  lyrics: Lyrics;
  lastModified: string;
  isSaving = false;
  isNew = true;
  isReadyToSave: boolean;

  form = this.formBuilder.group({
    id: [],
    songId: [null, []],
    lyrics: [null, []],
    lang: [null, []],
    author: [null, []],
    copyright: [null, []],
    isTranslated: ['false', []],
    translatedBy: [null, []],
    charset: [null, []],
    lastModified: [null, []],
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected navController: NavController,
    protected formBuilder: FormBuilder,
    public platform: Platform,
    protected toastCtrl: ToastController,
    private lyricsService: LyricsService
  ) {
    // Watch the form for changes, and
    this.form.valueChanges.subscribe((v) => {
      this.isReadyToSave = this.form.valid;
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe((response) => {
      this.lyrics = response.data;
      this.isNew = this.lyrics.id === null || this.lyrics.id === undefined;
      this.updateForm(this.lyrics);
    });
  }

  updateForm(lyrics: Lyrics) {
    this.form.patchValue({
      id: lyrics.id,
      songId: lyrics.songId,
      lyrics: lyrics.lyrics,
      lang: lyrics.lang,
      author: lyrics.author,
      copyright: lyrics.copyright,
      isTranslated: lyrics.isTranslated,
      translatedBy: lyrics.translatedBy,
      charset: lyrics.charset,
      lastModified: this.isNew ? new Date().toISOString() : lyrics.lastModified,
    });
  }

  save() {
    this.isSaving = true;
    const lyrics = this.createFromForm();
    if (!this.isNew) {
      this.subscribeToSaveResponse(this.lyricsService.update(lyrics));
    } else {
      this.subscribeToSaveResponse(this.lyricsService.create(lyrics));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<Lyrics>>) {
    result.subscribe(
      (res: HttpResponse<Lyrics>) => this.onSaveSuccess(res),
      (res: HttpErrorResponse) => this.onError(res.error)
    );
  }

  async onSaveSuccess(response) {
    let action = 'updated';
    if (response.status === 201) {
      action = 'created';
    }
    this.isSaving = false;
    const toast = await this.toastCtrl.create({ message: `Lyrics ${action} successfully.`, duration: 2000, position: 'middle' });
    toast.present();
    this.navController.navigateBack('/members/lyrics');
  }

  previousState() {
    window.history.back();
  }

  async onError(error) {
    this.isSaving = false;
    console.error(error);
    const toast = await this.toastCtrl.create({ message: 'Failed to load data', duration: 2000, position: 'middle' });
    toast.present();
  }

  private createFromForm(): Lyrics {
    return {
      ...new Lyrics(),
      id: this.form.get(['id']).value,
      songId: this.form.get(['songId']).value,
      lyrics: this.form.get(['lyrics']).value,
      lang: this.form.get(['lang']).value,
      author: this.form.get(['author']).value,
      copyright: this.form.get(['copyright']).value,
      isTranslated: this.form.get(['isTranslated']).value,
      translatedBy: this.form.get(['translatedBy']).value,
      charset: this.form.get(['charset']).value,
      lastModified: new Date(this.form.get(['lastModified']).value),
    };
  }

}
