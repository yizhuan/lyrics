import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { ApiService } from './api/api.service';
import { Account } from '../models/account'

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  
  private _userAccount: Account;
  authenticated: boolean;
  private authenticationState = new Subject<any>();

  get userAccount() {
    return this._userAccount;
  }
  constructor(private http: HttpClient) { }

  fetch(): Observable<HttpResponse<Account>> {
    return this.http.get<Account>(ApiService.API_URL + '/account', { observe: 'response' });
  }

  
  async fetchAccount(force?: boolean): Promise<any> {
    if (force === true) {
      this._userAccount = undefined;
    }

    // check and see if we have retrieved the userIdentity data from the server.
    // if we have, reuse it by immediately resolving
    if (this._userAccount) {
      return Promise.resolve(this._userAccount);
    }

    // retrieve the userIdentity data from the server, update the identity object, and then resolve.
    try {
      const response = await this.fetch()
        .toPromise();
      const account = response.body;
      if (account) {
        this._userAccount = account;
        this.authenticated = true;
        // After retrieve the account info, the language will be changed to
        // the user's preferred language configured in the account setting
        //const langKey = this.sessionStorage.retrieve('locale') || this.userIdentity.langKey;
        // this.languageService.changeLanguage(langKey);
      }
      else {
        this._userAccount = null;
        this.authenticated = false;
      }
      this.authenticationState.next(this._userAccount);
      return this._userAccount;
    }
    catch (err) {
      this._userAccount = null;
      this.authenticated = false;
      this.authenticationState.next(this._userAccount);
      return null;
    }
  }

}
