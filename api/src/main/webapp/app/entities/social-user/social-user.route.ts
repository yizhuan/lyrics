import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISocialUser, SocialUser } from 'app/shared/model/social-user.model';
import { SocialUserService } from './social-user.service';
import { SocialUserComponent } from './social-user.component';
import { SocialUserDetailComponent } from './social-user-detail.component';
import { SocialUserUpdateComponent } from './social-user-update.component';

@Injectable({ providedIn: 'root' })
export class SocialUserResolve implements Resolve<ISocialUser> {
  constructor(private service: SocialUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocialUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((socialUser: HttpResponse<SocialUser>) => {
          if (socialUser.body) {
            return of(socialUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SocialUser());
  }
}

export const socialUserRoute: Routes = [
  {
    path: '',
    component: SocialUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.socialUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocialUserDetailComponent,
    resolve: {
      socialUser: SocialUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.socialUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocialUserUpdateComponent,
    resolve: {
      socialUser: SocialUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.socialUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocialUserUpdateComponent,
    resolve: {
      socialUser: SocialUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.socialUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
