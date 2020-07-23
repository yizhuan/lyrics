import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { Song } from './song.model';
import { Lyrics } from '../lyrics/lyrics.model';

@Injectable({ providedIn: 'root' })
export class SongService {
  private resourceUrl = ApiService.API_URL + '/songs';

  constructor(protected http: HttpClient) {}

  create(song: Song): Observable<HttpResponse<Song>> {
    return this.http.post<Song>(this.resourceUrl, song, { observe: 'response' });
  }

  update(song: Song): Observable<HttpResponse<Song>> {
    return this.http.put(this.resourceUrl, song, { observe: 'response' });
  }

  find(id: number): Observable<HttpResponse<Song>> {
    return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findLyrics(id: number): Observable<HttpResponse<Lyrics[]>> {
    return this.http.get<Lyrics[]>(`${this.resourceUrl}/${id}/lyrics`, { observe: 'response' });
  }

  query(req?: any): Observable<HttpResponse<Song[]>> {
    const options = createRequestOption(req);
    return this.http.get<Song[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
