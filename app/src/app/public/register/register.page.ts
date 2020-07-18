import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AccountService } from 'src/app/services/account.service';
import { Account } from 'src/app/models/account';
import { ApiService } from 'src/app/services/api/api.service';
import { SupportedlanguagesService } from 'src/app/services/supportedlanguages.service';

@Component({
  selector: "app-register",
  templateUrl: "./register.page.html",
  styleUrls: ["./register.page.scss"],
})
export class RegisterPage implements OnInit {
  languages: any[];
  constructor(private authService: AuthenticationService, 
    private accountService: AccountService, 
    private apiService: ApiService,
    private supportedLanguages: SupportedlanguagesService
    ) { }

  ngOnInit() {
    this.languages = this.supportedLanguages.languages;    
  }

  onSubmit(form: NgForm) {

    console.log(form); 

    this.apiService.post(
      'register',
      {
        login: form.value.mobileNumber,
        activated: true,
        authorities: ["ROLE_USER"],
        createdBy: "admin",
        createdDate: new Date(),
        email: form.value.email.toLowerCase(),
        firstName: form.value.firstName,
        lastName: form.value.lastName,
        imageUrl: "",
        langKey: form.value.langKey,
        lastModifiedBy: "admin",
        lastModifiedDate: new Date(),
        password: form.value.password,
        mobileNumber: form.value.mobileNumber
      },
      { observe: 'response' }
    ).subscribe(response => {
      console.log("new customer registered.")
      //console.log(response);

      // this causes login page is shown swiftly
      // this.authService.logout();

      // login new customer
      this.authService.login({
        username: form.value.email.toLowerCase(),
        password: form.value.password,
        rememberMe: true
      }).subscribe(response => {
        console.log("new customer logged in");
        // console.log(response);

        this.accountService.fetchAccount(true).then((account: Account) => {
          console.log("userId: " + account.login);
        })

      });

    });

  }
}
