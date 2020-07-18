import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { AccountService } from 'src/app/services/account.service';
import { Router } from '@angular/router';
import { LoadingController } from '@ionic/angular';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.page.html',
  styleUrls: ['./dashboard.page.scss'],
})
export class DashboardPage implements OnInit {

  constructor(private authService: AuthenticationService, 
    private accountService: AccountService, 
    private router: Router,
    private loadingCtrl: LoadingController) { }

  ngOnInit() {

  }

  logout() {
    this.authService.logout();
    console.log("logged out!")
  }

}
