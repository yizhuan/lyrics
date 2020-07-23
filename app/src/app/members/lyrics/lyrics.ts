import { Component } from '@angular/core';
import { NavController, ToastController, Platform, IonItemSliding } from '@ionic/angular';
import { Lyrics } from './lyrics.model';
import { LyricsService } from './lyrics.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'page-lyrics',
  templateUrl: 'lyrics.html',
})
export class LyricsPage {
  lyrics: Lyrics[];

  // todo: add pagination

  constructor(
    private navController: NavController,
    private lyricsService: LyricsService,
    private toastCtrl: ToastController,
    private activatedRoute: ActivatedRoute,
    public plt: Platform
  ) {
    this.lyrics = [];
  }

  ngOnInit(): void {
    
  }

  async loadLyrics() {
    this.activatedRoute.data.subscribe((response) => {
      this.lyrics = response.data;
    });
  }
  
  ionViewWillEnter() {
    this.loadLyrics();
    //this.loadAll();
  }

  /*
  async loadAll(refresher?) {
    this.lyricsService
      .query()
      .pipe(
        filter((res: HttpResponse<Lyrics[]>) => res.ok),
        map((res: HttpResponse<Lyrics[]>) => res.body)
      )
      .subscribe(
        (response: Lyrics[]) => {
          this.lyrics = response;
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
  */

  trackId(index: number, item: Lyrics) {
    return item.id;
  }

  new() {
    this.navController.navigateForward('/members/lyrics/new');
  }

  edit(item: IonItemSliding, lyrics: Lyrics) {
    this.navController.navigateForward('/members/lyrics/' + lyrics.id + '/edit');
    item.close();
  }

  async delete(lyrics) {
    this.lyricsService.delete(lyrics.id).subscribe(
      async () => {
        const toast = await this.toastCtrl.create({ message: 'Lyrics deleted successfully.', duration: 3000, position: 'middle' });
        toast.present();
        // this.loadAll();
        this.loadLyrics();
      },
      (error) => console.error(error)
    );
  }

  view(lyrics: Lyrics) {
    this.navController.navigateForward('/members/lyrics/' + lyrics.id + '/view');
  }
}
