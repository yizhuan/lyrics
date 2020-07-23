import { Component, OnInit } from '@angular/core';
import { Song } from './song.model';
import { SongService } from './song.service';
import { NavController, AlertController } from '@ionic/angular';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'page-song-detail',
  templateUrl: 'song-detail.html',
})
export class SongDetailPage implements OnInit {
  song: Song = {};

  constructor(
    private navController: NavController,
    private songService: SongService,
    private activatedRoute: ActivatedRoute,
    private alertController: AlertController
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((response) => {
      this.song = response.data;
    });
  }

  open(item: Song) {
    this.navController.navigateForward('/members/song/' + item.id + '/edit');
  }

  async deleteModal(item: Song) {
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
            this.songService.delete(item.id).subscribe(() => {
              this.navController.navigateForward('/members/song');
            });
          },
        },
      ],
    });
    await alert.present();
  }
}
