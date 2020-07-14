import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ILyrics } from 'app/shared/model/lyrics.model';

type EntityResponseType = HttpResponse<ILyrics>;
type EntityArrayResponseType = HttpResponse<ILyrics[]>;

@Injectable({ providedIn: 'root' })
export class LyricsService {
  public resourceUrl = SERVER_API_URL + 'api/lyrics';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/lyrics';

  constructor(protected http: HttpClient) {}

  create(lyrics: ILyrics): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lyrics);
    return this.http
      .post<ILyrics>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lyrics: ILyrics): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lyrics);
    return this.http
      .put<ILyrics>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILyrics>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILyrics[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILyrics[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(lyrics: ILyrics): ILyrics {
    const copy: ILyrics = Object.assign({}, lyrics, {
      lastModified: lyrics.lastModified && lyrics.lastModified.isValid() ? lyrics.lastModified.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastModified = res.body.lastModified ? moment(res.body.lastModified) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lyrics: ILyrics) => {
        lyrics.lastModified = lyrics.lastModified ? moment(lyrics.lastModified) : undefined;
      });
    }
    return res;
  }
}
