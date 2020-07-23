import { Injectable } from '@angular/core';
import { Song } from 'src/app/models/song';
import {ZonedDateTime} from '@js-joda/core'

@Injectable({
  providedIn: 'root'
})
export class SongService{

  private songs: Song[] = [
    {
      id: 1,
      name: 'The First Thing I See',
      description: '',
      composedBy: '',
      artist: 'Kina Grannis',
      album: 'The First Thing I See (feat. Kina Grannis)',
      band: 'Imaginary Future',
      year: '2018',
      copyright: '',
      lang: 'en',
      audioUrl: '',
      videoUrl: 'https://www.youtube.com/watch?v=41Hw48h4FGQ',
      enteredBy: 'Yizhuan',
      lastModified: ZonedDateTime.now(),
    },
    {
      id: 2,
      name: '陪你一起变老',
      description: '',
      composedBy: '',
      artist: '唐古',
      album: '陪你一起变老',
      band: '唐古',
      year: '2016',
      copyright: '',
      lang: 'zh-CN',
      audioUrl: '',
      videoUrl: 'https://www.youtube.com/watch?v=Kddo2wclvvg',
      enteredBy: '',
      lastModified: ZonedDateTime.now(),
    },
  ];

  constructor() { }

  getSongs() {
    return [...this.songs];
  }

  getSong(songId: number) {
    return {...this.songs.find( song => {
      return song.id === songId;
    })
    };
  }

  find(name: string) {
    //TODO
    if (name == "test")
      return [...this.songs];
    else
      return null;
  }

}
