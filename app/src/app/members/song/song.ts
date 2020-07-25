import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { Song } from './song.model';
import { SongService } from './song.service';

@Component({
  selector: 'page-song',
  templateUrl: 'song.html',
})
export class SongPage {
  songs: Song[];

  // todo: add pagination

  constructor(
    private navController: NavController,
    private songService: SongService,
    private toastCtrl: ToastController,
    public plt: Platform
  ) {
    // this.songs = [];
    this.loadAll();
  }

  ionViewWillEnter() {
    this.loadAll();
  }

  async loadAll(refresher?) {
    this.songService
      .query()
      .pipe(
        filter((res: HttpResponse<Song[]>) => res.ok),
        map((res: HttpResponse<Song[]>) => res.body)
      )
      .subscribe(
        (response: Song[]) => {
          this.songs = response;
          if (typeof refresher !== 'undefined') {
            setTimeout(() => {
              refresher.target.complete();
            }, 750);
          }
        },
        async (error) => {
          console.error(error);
          const toast = await this.toastCtrl.create({ message: 'Failed to load data', duration: 2000, position: 'middle' });
          toast.present();
        }
      );
  }

  trackId(index: number, item: Song) {
    return item.id;
  }

  new() {
    this.navController.navigateForward('/members/song/new');
  }

  edit(item: IonItemSliding, song: Song) {
    this.navController.navigateForward('/members/song/' + song.id + '/edit');
    item.close();
  }

  async delete(song) {
    this.songService.delete(song.id).subscribe(
      async () => {
        const toast = await this.toastCtrl.create({ message: 'Song deleted successfully.', duration: 3000, position: 'middle' });
        toast.present();
        this.loadAll();
      },
      (error) => console.error(error)
    );
  }

  view(song: Song) {
    this.navController.navigateForward('/members/song/' + song.id + '/view');
  }
}
