import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { NgForm } from '@angular/forms';
import { AccountService } from 'src/app/services/account.service';
import { Account } from 'src/app/models/account';
import { SupportedlanguagesService } from 'src/app/services/supportedlanguages.service';
import { TranslateService } from '@ngx-translate/core';
import { SocialUser, SocialAuthService, GoogleLoginProvider, FacebookLoginProvider } from 'angularx-social-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  languages: any[];

  currentLanguage: string;

  isLogin: boolean;
  isBadCredential: boolean;

  private user: SocialUser;

  constructor(private authService: AuthenticationService,
    private accountService: AccountService,
    private supportedLanguages: SupportedlanguagesService,
    private translate: TranslateService,
    private socialAuthService: SocialAuthService) { }

  ngOnInit() {
    this.languages = this.supportedLanguages.languages;
    this.currentLanguage = this.languages[0].language;
    this.isLogin = true;
    this.isBadCredential = false;
  }

  onChangeLanguage(selectedValue: any) {
    for (let lang of this.languages) {
      if (lang.code === selectedValue.detail.value) {
        this.currentLanguage = lang.language;
        break;
      }
    };
    this.translate.use(selectedValue.detail.value);
  }

  onSwitchAuthMode() {
    this.isLogin = !this.isLogin;
  }

  onSubmit(form: NgForm) {
    // console.log(form);

    if (!form.valid) {
      return;
    }
    const login = form.value.login;
    const password = form.value.password;

    // console.log(email, password);

    if (this.isLogin) {
      // send req to login server
      this.authService.login({
        username: login,
        password: password,
        rememberMe: false
      }).subscribe(
        jwt => {
          // console.log(jwt);
          this.accountService.fetchAccount(true).then((account: Account) => {
            console.log("userId: " + account.login);
            console.log(account);
          })
        },
        err => {
          // console.log('login error: '+err);          
          this.isBadCredential = true;
          // console.log('isBadCredential: '+this.isBadCredential);
        });
    }
    else {
      // send req to signup server
    }
  }

  signInWithGoogle(): void {
    this.signInSocial(GoogleLoginProvider.PROVIDER_ID);
  }

  signInWithFB(): void {
    this.signInSocial(FacebookLoginProvider.PROVIDER_ID);
  }

  signInSocial(providerID: string) {
    this.socialAuthService.signIn(providerID).then((socialUser: SocialUser) => {
      this.authService.socialLogin(socialUser).subscribe(
        jwt => {
          // console.log("jwt: "+jwt);
          this.accountService.fetchAccount(true).then((account: Account) => {
            console.log("userId: " + account.login);
            // console.log(account);
          })
        }
      );
    });
  }

}
