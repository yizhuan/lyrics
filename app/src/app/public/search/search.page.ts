import { Component, OnInit } from '@angular/core';
import { Song } from 'src/app/members/song';
import { SongService } from 'src/app/members/song';
import { ActivatedRoute } from '@angular/router';
import { filter, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

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
      this.searchSongName(q).subscribe();      
    });
  }

  onSearchSong(event: any) {
    const queryString = event.target.value;
    this.searchSongName(queryString).subscribe();
  }

  searchSongName(q: string) {
    if (q) {
      console.log("searching: "+q);   
      return this.songService.query({'name.contains': q})
        .pipe(
          filter((res: HttpResponse<Song[]>) => res.ok),
          map((res: HttpResponse<Song[]>) => this.searchResults = res.body)
        );
    }
    return of(<Song[]>[]);
  }

}
