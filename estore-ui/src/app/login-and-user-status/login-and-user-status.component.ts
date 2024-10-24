import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-and-user-status',
  templateUrl: './login-and-user-status.component.html',
  styleUrls: ['./login-and-user-status.component.css']
})
export class LoginAndUserStatusComponent implements OnInit {

  userText: String | undefined;
  logoutText: String = "Log out";
  isLogin: boolean | undefined;

  constructor(
    public webMessage: WebMessageService,
    private router: Router
  )
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){// 0 = not log in
      this.userText = "Please log in";
      this.isLogin = false;
    }else if(sessionStorage.getItem("loginStatus") == "1"){// 1 = log in as customer
      this.userText = sessionStorage.getItem("loginInfo") as string;
      this.isLogin = true;
    }else if(sessionStorage.getItem("loginStatus") == "2"){// 2 = log in as admin
      this.userText = sessionStorage.getItem("loginInfo") as string;
      this.isLogin = true;
    }
  }

  onClick(): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\customer-profile');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  logout(): void{
    sessionStorage.setItem("loginStatus","0");
    sessionStorage.removeItem("userID");
    sessionStorage.removeItem("loginInfo");
    this.router.navigateByUrl('\menu');
    this.userText = "Please log in";
    this.isLogin = false;
    this.webMessage.isCartVisible = false;
  }
}
