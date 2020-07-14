import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISong, Song } from 'app/shared/model/song.model';
import { SongService } from './song.service';

@Component({
  selector: 'jhi-song-update',
  templateUrl: './song-update.component.html',
})
export class SongUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    composedBy: [],
    artist: [],
    album: [],
    band: [],
    year: [],
    copyright: [],
    lang: [],
    audioUrl: [],
    videoUrl: [],
    enteredBy: [],
    lastModified: [],
  });

  constructor(protected songService: SongService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ song }) => {
      if (!song.id) {
        const today = moment().startOf('day');
        song.lastModified = today;
      }

      this.updateForm(song);
    });
  }

  updateForm(song: ISong): void {
    this.editForm.patchValue({
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
      lastModified: song.lastModified ? song.lastModified.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const song = this.createFromForm();
    if (song.id !== undefined) {
      this.subscribeToSaveResponse(this.songService.update(song));
    } else {
      this.subscribeToSaveResponse(this.songService.create(song));
    }
  }

  private createFromForm(): ISong {
    return {
      ...new Song(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      composedBy: this.editForm.get(['composedBy'])!.value,
      artist: this.editForm.get(['artist'])!.value,
      album: this.editForm.get(['album'])!.value,
      band: this.editForm.get(['band'])!.value,
      year: this.editForm.get(['year'])!.value,
      copyright: this.editForm.get(['copyright'])!.value,
      lang: this.editForm.get(['lang'])!.value,
      audioUrl: this.editForm.get(['audioUrl'])!.value,
      videoUrl: this.editForm.get(['videoUrl'])!.value,
      enteredBy: this.editForm.get(['enteredBy'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISong>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
