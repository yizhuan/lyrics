import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILyrics, Lyrics } from 'app/shared/model/lyrics.model';
import { LyricsService } from './lyrics.service';
import { LyricsComponent } from './lyrics.component';
import { LyricsDetailComponent } from './lyrics-detail.component';
import { LyricsUpdateComponent } from './lyrics-update.component';

@Injectable({ providedIn: 'root' })
export class LyricsResolve implements Resolve<ILyrics> {
  constructor(private service: LyricsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILyrics> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lyrics: HttpResponse<Lyrics>) => {
          if (lyrics.body) {
            return of(lyrics.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lyrics());
  }
}

export const lyricsRoute: Routes = [
  {
    path: '',
    component: LyricsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.lyrics.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LyricsDetailComponent,
    resolve: {
      lyrics: LyricsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.lyrics.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LyricsUpdateComponent,
    resolve: {
      lyrics: LyricsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.lyrics.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LyricsUpdateComponent,
    resolve: {
      lyrics: LyricsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'lyricsApp.lyrics.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
