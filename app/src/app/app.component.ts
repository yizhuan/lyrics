import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { Router } from '@angular/router';
import { AuthenticationService } from './services/authentication.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private authenticationService: AuthenticationService,
    private router: Router,
    private translate: TranslateService
  ) {
    this.translate.setDefaultLang('zh_CN');
    this.translate.use('zh_CN');

    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();

      this.authenticationService.authenticationState.subscribe(state => {
        console.log(this.authenticationService.userId + "=====state changed: " + state)

        if (this.authenticationService.userId != null && state) {
          this.router.navigate(['members', 'dashboard']);
        } // else {
          // this.router.navigate(['login']);
          // this.router.navigate(['home']);
        // }

      });

    });
  }

  onLogout() {
    this.authenticationService.logout();
  }
}
