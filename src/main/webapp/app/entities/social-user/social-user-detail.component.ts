import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISocialUser } from 'app/shared/model/social-user.model';

@Component({
  selector: 'jhi-social-user-detail',
  templateUrl: './social-user-detail.component.html',
})
export class SocialUserDetailComponent implements OnInit {
  socialUser: ISocialUser | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socialUser }) => (this.socialUser = socialUser));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
