import { NgModule, Injectable } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';

import { SongPage } from './song';
import { SongUpdatePage } from './song-update';
import { Song, SongService, SongDetailPage } from '.';
import { AuthGuard } from 'src/app/guards/auth.guard';

@Injectable({ providedIn: 'root' })
export class SongResolve implements Resolve<Song> {
  constructor(private service: SongService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Song> {
    const id = route.params.id ? route.params.id : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Song>) => response.ok),
        map((song: HttpResponse<Song>) => song.body)
      );
    }
    return of(new Song());
  }
}

const routes: Routes = [
  {
    path: '',
    component: SongPage,
    // data: {
    //   authorities: ['ROLE_USER'],
    // },
    // canActivate: [AuthGuard],
  },
  {
    path: 'new',
    component: SongUpdatePage,
    resolve: {
      data: SongResolve,
    },
    // data: {
      // authorities: ['ROLE_USER'],
    // },
    // canActivate: [AuthGuard],
  },
  {
    path: ':id/view',
    component: SongDetailPage,
    resolve: {
      data: SongResolve,
    },
    // data: {
      // authorities: ['ROLE_USER'],
    // },
    // canActivate: [AuthGuard],
  },
  {
    path: ':id/edit',
    component: SongUpdatePage,
    resolve: {
      data: SongResolve,
    },
    // data: {
      // authorities: ['ROLE_USER'],
    // },
    // canActivate: [AuthGuard],
  },
];

@NgModule({
  declarations: [SongPage, SongUpdatePage, SongDetailPage],
  imports: [IonicModule, FormsModule, ReactiveFormsModule, CommonModule, TranslateModule, RouterModule.forChild(routes)],
})
export class SongPageModule {}
