import { Component, OnInit } from '@angular/core';
import { Song } from 'src/app/models/song';
import { SongService } from '../../services/lyrics/song.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.page.html',
  styleUrls: ['./search.page.scss'],
})

export class SearchPage implements OnInit {

  searchResults: Song[] = [];

  constructor(private songService: SongService, private route: ActivatedRoute,) { }

  ngOnInit() {

    this.route.queryParams.subscribe(params => {
      const q = params['q'];
      this.searchResults = this.songService.find(q);
    });

  }

  onSearchSong(event: any) {
    const val = event.target.value;
    console.log(val);   
    this.searchResults = this.songService.find(val);
  }
}
