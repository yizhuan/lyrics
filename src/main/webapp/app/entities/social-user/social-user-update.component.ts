import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ISocialUser, SocialUser } from 'app/shared/model/social-user.model';
import { SocialUserService } from './social-user.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-social-user-update',
  templateUrl: './social-user-update.component.html',
})
export class SocialUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    provider: [],
    socialUserId: [],
    email: [],
    name: [],
    photoUrl: [],
    firstName: [],
    lastName: [],
    authToken: [],
    idToken: [],
    authorizationCode: [],
    facebook: [],
    linkedIn: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected socialUserService: SocialUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socialUser }) => {
      this.updateForm(socialUser);
    });
  }

  updateForm(socialUser: ISocialUser): void {
    this.editForm.patchValue({
      id: socialUser.id,
      provider: socialUser.provider,
      socialUserId: socialUser.socialUserId,
      email: socialUser.email,
      name: socialUser.name,
      photoUrl: socialUser.photoUrl,
      firstName: socialUser.firstName,
      lastName: socialUser.lastName,
      authToken: socialUser.authToken,
      idToken: socialUser.idToken,
      authorizationCode: socialUser.authorizationCode,
      facebook: socialUser.facebook,
      linkedIn: socialUser.linkedIn,
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
    const socialUser = this.createFromForm();
    if (socialUser.id !== undefined) {
      this.subscribeToSaveResponse(this.socialUserService.update(socialUser));
    } else {
      this.subscribeToSaveResponse(this.socialUserService.create(socialUser));
    }
  }

  private createFromForm(): ISocialUser {
    return {
      ...new SocialUser(),
      id: this.editForm.get(['id'])!.value,
      provider: this.editForm.get(['provider'])!.value,
      socialUserId: this.editForm.get(['socialUserId'])!.value,
      email: this.editForm.get(['email'])!.value,
      name: this.editForm.get(['name'])!.value,
      photoUrl: this.editForm.get(['photoUrl'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      authToken: this.editForm.get(['authToken'])!.value,
      idToken: this.editForm.get(['idToken'])!.value,
      authorizationCode: this.editForm.get(['authorizationCode'])!.value,
      facebook: this.editForm.get(['facebook'])!.value,
      linkedIn: this.editForm.get(['linkedIn'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocialUser>>): void {
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
