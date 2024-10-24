import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebMessageService } from '../web-message.service';

@Component({
  selector: 'app-logo-mid',
  templateUrl: './logo-mid.component.html',
  styleUrls: ['./logo-mid.component.css']
})
export class LogoMidComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService,
    private router: Router
  )
  {

  }

  ngOnInit(): void {
  }

  backToMenu(): void{
    if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }else{
      this.router.navigateByUrl('\menu');
    }
  }

}
