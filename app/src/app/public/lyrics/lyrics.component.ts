import { Component, OnInit, Input } from '@angular/core';
import { Song } from 'src/app/members/song';
import { Lyrics } from 'src/app/members/lyrics';
import { SongService } from 'src/app/members/song';
import { LyricsService } from 'src/app/members/lyrics';
import { filter, map, find } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-lyrics',
  templateUrl: './lyrics.component.html',
  styleUrls: ['./lyrics.component.scss'],
  // inputs: ['songId','lang'],
})
export class LyricsComponent implements OnInit {

  @Input() songId: number;
  @Input() lang: string;

  song: Song;
  lyrics: Lyrics;
  languages: string[] = [];

  selectedLang: string;

  constructor(private songservice: SongService, 
    private lyricsService: LyricsService) { 
      
  }

  ngOnInit() {
    // console.log("****lyrics-ngOnInit*******ngOnChanges************");
    // this.load();    
  }

  ngOnChanges() {
    console.log("****lyrics-ngOnChanges*******ngOnChanges************");
    console.log('songId=', this.songId);
    console.log('lang=', this.lang);
    this.load();
  }

  ionViewWillEnter() {
    // console.log("****lyrics-ionViewWillEnter*******ngOnChanges************");
    // this.load();
  }


  load() {
    console.log("songId="+this.songId);
    if (this.songId === undefined) {
      return;
    }
    console.log("loading lyrics...");
    this.songservice.find(this.songId).pipe(
      filter((res: HttpResponse<Song>) => res.ok),
      map((res: HttpResponse<Song>) => {
        this.song = res.body;
      
        this.songservice.findLyrics(this.songId).pipe(
          filter((res: HttpResponse<Lyrics[]>) => res.ok),
          map((res: HttpResponse<Lyrics[]>) => {
            const lys: Lyrics[] = res.body;
            lys.forEach((ly: Lyrics) => {
              if (!this.languages.includes(ly.lang)) {
                this.languages.push(ly.lang);
              }
              if (ly.lang == this.lang) {
                this.lyrics = ly;
              }
            });
            this.selectedLang = this.lang;
          })
        ).subscribe();           

      })
    ).subscribe();
  }

  onSwitchLanguage(lang: string) {

    this.songservice.findOneLyrics(this.songId, lang).pipe(
      filter((res: HttpResponse<Lyrics>) => res.ok),
      map((res: HttpResponse<Lyrics>) => {
        this.lyrics = res.body;
        this.selectedLang = lang;
      })
    ).subscribe();
  }

}
