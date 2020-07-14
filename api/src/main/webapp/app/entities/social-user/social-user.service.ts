import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ISocialUser } from 'app/shared/model/social-user.model';

type EntityResponseType = HttpResponse<ISocialUser>;
type EntityArrayResponseType = HttpResponse<ISocialUser[]>;

@Injectable({ providedIn: 'root' })
export class SocialUserService {
  public resourceUrl = SERVER_API_URL + 'api/social-users';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/social-users';

  constructor(protected http: HttpClient) {}

  create(socialUser: ISocialUser): Observable<EntityResponseType> {
    return this.http.post<ISocialUser>(this.resourceUrl, socialUser, { observe: 'response' });
  }

  update(socialUser: ISocialUser): Observable<EntityResponseType> {
    return this.http.put<ISocialUser>(this.resourceUrl, socialUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISocialUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocialUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocialUser[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
