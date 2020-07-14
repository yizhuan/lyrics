import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ISong } from 'app/shared/model/song.model';

type EntityResponseType = HttpResponse<ISong>;
type EntityArrayResponseType = HttpResponse<ISong[]>;

@Injectable({ providedIn: 'root' })
export class SongService {
  public resourceUrl = SERVER_API_URL + 'api/songs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/songs';

  constructor(protected http: HttpClient) {}

  create(song: ISong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(song);
    return this.http
      .post<ISong>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(song: ISong): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(song);
    return this.http
      .put<ISong>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISong>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISong[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISong[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(song: ISong): ISong {
    const copy: ISong = Object.assign({}, song, {
      lastModified: song.lastModified && song.lastModified.isValid() ? song.lastModified.toJSON() : undefined,
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
      res.body.forEach((song: ISong) => {
        song.lastModified = song.lastModified ? moment(song.lastModified) : undefined;
      });
    }
    return res;
  }
}
