import { NgModule, Injectable } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

import { LyricsPage } from './lyrics';
import { LyricsUpdatePage } from './lyrics-update';
import { Lyrics, LyricsService, LyricsDetailPage } from '.';
import { AuthGuard } from 'src/app/guards/auth.guard';
import { SongService } from '../song/song.service';
import { QuillModule } from 'ngx-quill';

@Injectable({ providedIn: 'root' })
export class LyricsResolve implements Resolve<Lyrics> {
  constructor(private service: LyricsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lyrics> {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Lyrics>) => response.ok),
        map((lyrics: HttpResponse<Lyrics>) => lyrics.body)
      );
    }

    const songId = route.queryParams.songId? route.queryParams.songId : null;
    console.log("*****songId="+songId);
    const ly: Lyrics = new Lyrics();
    ly.songId = songId;
    return of(ly);
  }
}

@Injectable({ providedIn: 'root' })
export class LyricsListResolve implements Resolve<Lyrics[]> {
  constructor(private songService: SongService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Lyrics[]> {
    const songId = route.queryParams.songId? route.queryParams.songId : null;
    console.log("*****songId="+songId);
    if (songId) {
      return this.songService.findLyrics(songId).pipe(
        filter((res: HttpResponse<Lyrics[]>) => res.ok),
        map((res: HttpResponse<Lyrics[]>) => res.body)
      );
    }

    return of(<Lyrics[]>[]);
  }
}

const routes: Routes = [
  {
    path: '',
    component: LyricsPage,
    resolve: {
      data: LyricsListResolve,
    },    
    data: {
      authorities: ['ROLE_USER'],
    },
    // canActivate: [AuthGuard],
  },
  {
    path: 'new',
    component: LyricsUpdatePage,
    resolve: {
      data: LyricsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
    },
    // canActivate: [AuthGuard],
  },
  {
    path: ':id/view',
    component: LyricsDetailPage,
    resolve: {
      data: LyricsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
    },
    // canActivate: [AuthGuard],
  },
  {
    path: ':id/edit',
    component: LyricsUpdatePage,
    resolve: {
      data: LyricsResolve,
    },
    data: {
      authorities: ['ROLE_USER'],
    },
    // canActivate: [AuthGuard],
  },
];

@NgModule({
  declarations: [LyricsPage, LyricsUpdatePage, LyricsDetailPage],
  imports: [IonicModule, FormsModule, QuillModule, ReactiveFormsModule, CommonModule, TranslateModule, RouterModule.forChild(routes)],
})
export class LyricsPageModule {}
