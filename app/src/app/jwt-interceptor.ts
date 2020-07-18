import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './services/authentication.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private authenticationService: AuthenticationService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add auth header with jwt if user is logged in and request is to api url
        // const isApiUrl = request.url.startsWith(config.apiUrl);
        //if (isLoggedIn && isApiUrl) {
        if (this.authenticationService.isAuthenticated) {
            const jwt = this.authenticationService.currentUserToken;
            // console.log("JwtInterceptor token set: "+jwt);
            request = request.clone({
                setHeaders: {
                    Authorization: `Bearer ${jwt}`
                }
            });
        }

        return next.handle(request);
    }
}