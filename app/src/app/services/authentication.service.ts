import { Platform } from '@ionic/angular';
import { Injectable } from '@angular/core';

import { map, catchError, switchMap, tap } from 'rxjs/operators';

import { Storage } from '@ionic/storage';
import { SessionStorageService } from 'ngx-webstorage';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { ApiService } from './api/api.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Account } from '../models/account';
import { SocialUser } from 'angularx-social-login';
import { User } from '../models/user';


const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authenticationState = new BehaviorSubject(false);

  private _userId: string;
  private _currentUserToken: string;

  get userId() {
    return this._userId;
  }

  get currentUserToken() {
    return this._currentUserToken;
  }

  constructor(private http: HttpClient, private $localStorage: Storage, private $sessionStorage: SessionStorageService, private plt: Platform) {
    this.plt.ready().then(() => {
      this.checkToken();
    });
  }

  checkToken() {
    this.$localStorage.get(TOKEN_KEY).then((value) => {
      if (value) {
        this.authenticationState.next(true);
      }
      else {
        this.$sessionStorage.observe(TOKEN_KEY).subscribe((value) => {
          if (value) {
            this.authenticationState.next(true);
          }
        });
      }
    })
  }

  isAuthenticated() {
    return this.authenticationState.value;
  }

  login(credentials: any): Observable<any> {
    const data = {
      username: credentials.username,
      password: credentials.password,
      rememberMe: credentials.rememberMe
    };

    return this.http.post(ApiService.API_URL + '/authenticate', data, { observe: 'response' }).pipe(
      map(authenticateSuccess.bind(this))
    );

    function authenticateSuccess(resp) {
      // console.log('entering authenticateSuccess...');
      // console.log(resp);
      // console.log(resp.body);  // id_token
      // console.log(resp.body.id_token);  // id_token
      const bearerToken = resp.headers.get('Authorization');
      if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
        const jwt = bearerToken.slice(7, bearerToken.length);
        // console.log(jwt);
        this.storeAuthenticationToken(jwt, credentials.rememberMe);

        this._userId = credentials.username;
        this._currentUserToken = jwt;

        this.authenticationState.next(true);
        console.log("Logged in user: " + credentials.username)
        return jwt;
      }
    }
  }

  socialLogin(socialUser: SocialUser): Observable<String> {
    // return this.http.post(ApiService.API_URL + '/auth', {...socialUser, idToken: ""}, { observe: 'response' }).pipe(
    return this.http.post(ApiService.API_URL + '/auth', socialUser, { observe: 'response' }).pipe(
      map(resp => {
        const bearerToken = resp.headers.get('Authorization');
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          // console.log(jwt);
          this.storeAuthenticationToken(jwt, true);

          this._userId = socialUser.email;
          this._currentUserToken = jwt;

          this.authenticationState.next(true);
          console.log("Logged in user: " + socialUser.email)
          return jwt;
        }
      })
    );
  }

  /*
  socialLogin(socialUser: SocialUser): Observable<String> {
    const pw = "rthu7y-854hFiojsed784gbb+=!jkbGjksrd*($6bjhetfhkL_3nmbsdfghut4543hihio@led#ssw";
    return this.http.get<User>(ApiService.API_URL + '/email/' + socialUser.email + "?id="
      + "10f10(dfce8)b9e23?8c2ce_fd8f4Nb4ea693a7eKa2Kd5eacf9e58Lb1d526820466a77ab")
      .pipe(
        tap(userData => {
          console.log("found existing user: "+userData.email);
        }
        ),
        catchError(
          error => {
            if (error.status == 404) {
              console.log("Registering new user ..."+socialUser.email);
              return this.http.post(
                ApiService.API_URL+'/register',
                {
                  login: socialUser.email,
                  activated: true,
                  authorities: ["ROLE_USER"],
                  createdBy: "admin",
                  createdDate: new Date(),
                  email: socialUser.email,
                  firstName: socialUser.firstName,
                  lastName: socialUser.lastName,
                  imageUrl: socialUser.photoUrl,
                  langKey: "en",
                  lastModifiedBy: "admin",
                  lastModifiedDate: new Date(),
                  password: pw,
                  mobileNumber: ""
                },
                { observe: 'response' }
              )
            }
            else {
              return of([]);
            }
          }
        ),
        switchMap(
          res => {
            console.log("switch to login");
            return this.login(
              {
                username: socialUser.email,
                password: pw,
                rememberMe: true
              });
          }
        )
      )    
  }
  */

  loginWithToken(jwt: string, rememberMe: boolean) {
    if (jwt) {
      this.storeAuthenticationToken(jwt, rememberMe);
      return Promise.resolve(jwt);
    } else {
      return Promise.reject('auth-jwt-service Promise reject'); // Put appropriate error message here
    }
  }

  storeAuthenticationToken(jwt: string, rememberMe: boolean) {
    if (rememberMe) {
      this.$localStorage.set(TOKEN_KEY, jwt);
    } else {
      this.$sessionStorage.store(TOKEN_KEY, jwt);
    }
  }

  logout(): Observable<any> {
    console.log("Auth logout called.");
    this.$sessionStorage.clear(TOKEN_KEY);
    this.$localStorage.remove(TOKEN_KEY).then(() => {
    });
    this.authenticationState.next(false);
    console.log("logging out...")

    return new Observable(observer => {
      this._userId = null;
      this._currentUserToken = null;

      observer.complete();
    });
  }

}
