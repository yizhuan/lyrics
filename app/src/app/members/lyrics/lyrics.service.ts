import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/services/api/api.service';
import { createRequestOption } from 'src/app/shared';
import { Lyrics } from './lyrics.model';

@Injectable({ providedIn: 'root' })
export class LyricsService {
  private resourceUrl = ApiService.API_URL + '/lyrics';

  constructor(protected http: HttpClient) {}

  create(lyrics: Lyrics): Observable<HttpResponse<Lyrics>> {
    return this.http.post<Lyrics>(this.resourceUrl, lyrics, { observe: 'response' });
  }

  update(lyrics: Lyrics): Observable<HttpResponse<Lyrics>> {
    return this.http.put(this.resourceUrl, lyrics, { observe: 'response' });
  }

  find(id: number): Observable<HttpResponse<Lyrics>> {
    return this.http.get(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<HttpResponse<Lyrics[]>> {
    const options = createRequestOption(req);
    return this.http.get<Lyrics[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
