import { Component, OnInit } from '@angular/core';

import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  songId: number;
  lang: string;

  constructor(private router: Router, private route: ActivatedRoute) { 
    this.songId=11;
    this.lang="en";
  }

  ngOnInit() {  
    console.log("----ngOnInit----");
    this.retrieveUrlParams();
  }

  ionViewWillEnter() {
 
  }

  retrieveUrlParams() {
    this.route.queryParams.subscribe(params => {
      console.log("----params----");
      console.log(params);
      console.log("-----params-----");
      if (params['songId'] != null ) {
        this.songId = params['songId'];
        this.lang = params['lang'];
      }
    });
  }

  onSearchSong(event: any) {
    const val = event.target.value;
    console.log(val);
    this.router.navigate(['/search'], { queryParams: { q: val } });
  }

}
