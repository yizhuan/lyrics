import { Component, OnInit } from '@angular/core';
import { Lyrics } from './lyrics.model';
import { LyricsService } from './lyrics.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'page-lyrics-detail',
  templateUrl: 'lyrics-detail.html',
})
export class LyricsDetailPage implements OnInit {
  lyrics: Lyrics = {};

  constructor(
    private navController: NavController,
    private lyricsService: LyricsService,
    private activatedRoute: ActivatedRoute,
    private alertController: AlertController
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((response) => {
      this.lyrics = response.data;
    });
  }

  open(item: Lyrics) {
    this.navController.navigateForward('/members/lyrics/' + item.id + '/edit');
  }

  async deleteModal(item: Lyrics) {
    const alert = await this.alertController.create({
      header: 'Confirm the deletion?',
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
        },
        {
          text: 'Delete',
          handler: () => {
            this.lyricsService.delete(item.id).subscribe(() => {
              this.navController.navigateForward('/members/lyrics');
            });
          },
        },
      ],
    });
    await alert.present();
  }

}
