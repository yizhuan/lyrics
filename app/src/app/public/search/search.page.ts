import { Component, OnInit } from '@angular/core';
import { Song } from 'src/app/members/song';
import { SongService } from 'src/app/members/song';
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
    const queryString = event.target.value;
    console.log(queryString);   
    this.searchResults = this.songService.find(queryString);
  }
}
