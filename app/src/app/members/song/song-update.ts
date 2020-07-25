import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { NavController, Platform, ToastController } from '@ionic/angular';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Song } from './song.model';
import { SongService } from './song.service';

@Component({
  selector: 'page-song-update',
  templateUrl: 'song-update.html',
})
export class SongUpdatePage implements OnInit {
  song: Song;
  lastModified: string;
  isSaving = false;
  isNew = true;
  isReadyToSave: boolean;

  form = this.formBuilder.group({
    id: [],
    name: [null, []],
    description: [null, []],
    composedBy: [null, []],
    artist: [null, []],
    album: [null, []],
    band: [null, []],
    year: [null, []],
    copyright: [null, []],
    lang: [null, []],
    audioUrl: [null, []],
    videoUrl: [null, []],
    enteredBy: [null, []],
    lastModified: [null, []],
  });

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected navController: NavController,
    protected formBuilder: FormBuilder,
    public platform: Platform,
    protected toastCtrl: ToastController,
    private songService: SongService
  ) {
    // Watch the form for changes, and
    this.form.valueChanges.subscribe((v) => {
      this.isReadyToSave = this.form.valid;
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe((response) => {
      this.song = response.data;
      this.isNew = this.song.id === null || this.song.id === undefined;
      this.updateForm(this.song);
    });
  }

  updateForm(song: Song) {
    this.form.patchValue({
      id: song.id,
      name: song.name,
      description: song.description,
      composedBy: song.composedBy,
      artist: song.artist,
      album: song.album,
      band: song.band,
      year: song.year,
      copyright: song.copyright,
      lang: song.lang,
      audioUrl: song.audioUrl,
      videoUrl: song.videoUrl,
      enteredBy: song.enteredBy,
      lastModified: this.isNew ? new Date().toISOString() : song.lastModified,
    });
  }

  save() {
    this.isSaving = true;
    const song = this.createFromForm();
    if (!this.isNew) {
      this.subscribeToSaveResponse(this.songService.update(song));
    } else {
      this.subscribeToSaveResponse(this.songService.create(song));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<Song>>) {
    result.subscribe(
      (res: HttpResponse<Song>) => this.onSaveSuccess(res),
      (res: HttpErrorResponse) => this.onError(res.error)
    );
  }

  async onSaveSuccess(response) {
    let action = 'updated';
    if (response.status === 201) {
      action = 'created';
    }
    this.isSaving = false;
    const toast = await this.toastCtrl.create({ message: `Song ${action} successfully.`, duration: 2000, position: 'middle' });
    toast.present();
    this.navController.navigateBack('/members/song');
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

  private createFromForm(): Song {
    return {
      ...new Song(),
      id: this.form.get(['id']).value,
      name: this.form.get(['name']).value,
      description: this.form.get(['description']).value,
      composedBy: this.form.get(['composedBy']).value,
      artist: this.form.get(['artist']).value,
      album: this.form.get(['album']).value,
      band: this.form.get(['band']).value,
      year: this.form.get(['year']).value,
      copyright: this.form.get(['copyright']).value,
      lang: this.form.get(['lang']).value,
      audioUrl: this.form.get(['audioUrl']).value,
      videoUrl: this.form.get(['videoUrl']).value,
      enteredBy: this.form.get(['enteredBy']).value,
      lastModified: new Date(this.form.get(['lastModified']).value),
    };
  }
}
