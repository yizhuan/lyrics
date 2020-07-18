import { Component, OnInit, Input } from '@angular/core';
import { Song } from 'src/app/models/song';
import { Lyrics } from 'src/app/models/lyrics';
import { SongService } from 'src/app/services/lyrics/song.service';
import { LyricsService } from 'src/app/services/lyrics/lyrics.service';


@Component({
  selector: 'app-lyrics',
  templateUrl: './lyrics.component.html',
  styleUrls: ['./lyrics.component.scss'],
})
export class LyricsComponent implements OnInit {

  @Input() songId: number;
  @Input() lang: string;

  song: Song;
  lyrics: Lyrics;
  languages: string[];

  selectedLang: string;

  constructor(private songservice: SongService, private lyricsService: LyricsService) { }

  ngOnInit() {
    this.song = this.songservice.getSong(this.songId);
    this.lyrics = this.lyricsService.getLyrics(this.songId, this.lang); 
    this.selectedLang = this.lang;
    this.languages = this.lyricsService.getLanguages(this.songId);
  }

  onSwitchLanguage(lang: string) {
    this.lyrics = this.lyricsService.getLyrics(this.songId, lang);
    this.selectedLang = lang;
  }

}
