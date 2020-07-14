import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILyrics, Lyrics } from 'app/shared/model/lyrics.model';
import { LyricsService } from './lyrics.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-lyrics-update',
  templateUrl: './lyrics-update.component.html',
})
export class LyricsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    songId: [],
    lyrics: [],
    lang: [],
    author: [],
    copyright: [],
    isTranslated: [],
    translatedBy: [],
    charset: [],
    lastModified: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected lyricsService: LyricsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lyrics }) => {
      if (!lyrics.id) {
        const today = moment().startOf('day');
        lyrics.lastModified = today;
      }

      this.updateForm(lyrics);
    });
  }

  updateForm(lyrics: ILyrics): void {
    this.editForm.patchValue({
      id: lyrics.id,
      songId: lyrics.songId,
      lyrics: lyrics.lyrics,
      lang: lyrics.lang,
      author: lyrics.author,
      copyright: lyrics.copyright,
      isTranslated: lyrics.isTranslated,
      translatedBy: lyrics.translatedBy,
      charset: lyrics.charset,
      lastModified: lyrics.lastModified ? lyrics.lastModified.format(DATE_TIME_FORMAT) : null,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('lyricsApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lyrics = this.createFromForm();
    if (lyrics.id !== undefined) {
      this.subscribeToSaveResponse(this.lyricsService.update(lyrics));
    } else {
      this.subscribeToSaveResponse(this.lyricsService.create(lyrics));
    }
  }

  private createFromForm(): ILyrics {
    return {
      ...new Lyrics(),
      id: this.editForm.get(['id'])!.value,
      songId: this.editForm.get(['songId'])!.value,
      lyrics: this.editForm.get(['lyrics'])!.value,
      lang: this.editForm.get(['lang'])!.value,
      author: this.editForm.get(['author'])!.value,
      copyright: this.editForm.get(['copyright'])!.value,
      isTranslated: this.editForm.get(['isTranslated'])!.value,
      translatedBy: this.editForm.get(['translatedBy'])!.value,
      charset: this.editForm.get(['charset'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value
        ? moment(this.editForm.get(['lastModified'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILyrics>>): void {
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
