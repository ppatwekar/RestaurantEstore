import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebMessageService } from '../web-message.service';

@Component({
  selector: 'app-right-icons',
  templateUrl: './right-icons.component.html',
  styleUrls: ['./right-icons.component.css']
})
export class RightIconsComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService,
    private router: Router
  )
  {

  }

  ngOnInit(): void {

  }


  navigateToOrder(): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\order-overview');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }

  }

  navigateToShoppingCart(): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\shopping-cart');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }

  }

  navigateToUser(): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\customer-profile');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }
}
