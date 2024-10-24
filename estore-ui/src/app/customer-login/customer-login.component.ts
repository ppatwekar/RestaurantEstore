import { Observable } from 'rxjs';
import { Customer } from './../customer';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebMessageService } from '../web-message.service';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-customer-login',
  templateUrl: './customer-login.component.html',
  styleUrls: ['./customer-login.component.css']
})
export class CustomerLoginComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService,
    private customerService: CustomerService,
    private router: Router
  )
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\menu');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  customerLogin(username: string, password: string): void{
    this.customerService.getCustomerByUsernameAndPassword(username,password).subscribe(customer =>{
      if(customer == null){
        alert("Wrong username or password! Please register an account if you don't have one.");
      }else{
        sessionStorage.setItem("loginStatus", "1");
        sessionStorage.setItem("loginInfo", `Hey! ${customer.firstName}`);
        sessionStorage.setItem("userID", customer.id.toString());
        this.router.navigateByUrl('\menu');
      }}
    );
  }

  goRegistrationPage(): void{
    this.router.navigateByUrl('\customer-registration');
  }

  goAdminLoginPage(): void{
    this.router.navigateByUrl('\admin-login');
  }
}
