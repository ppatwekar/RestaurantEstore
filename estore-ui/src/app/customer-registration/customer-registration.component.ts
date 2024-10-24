import { Customer } from './../customer';
import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';

@Component({
  selector: 'app-customer-registration',
  templateUrl: './customer-registration.component.html',
  styleUrls: ['./customer-registration.component.css']
})
export class CustomerRegistrationComponent implements OnInit {


  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private customerService: CustomerService
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

  customerRegister(username: string, password: string, firstName: string, lastName: string, email: string, phone: string, address: string): void{
    if(username == "" || password == "" || firstName == "" || lastName == "" || email == "" || phone == "" || address == ""){
      alert("Please finish all input fields!");
      return;
    }
    const newCustomer = {
      username: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      address: address
    }
    this.customerService.addCustomer(newCustomer as Customer).subscribe(customer=>{
      if(customer == null){
        alert("Username already exists!");
      }else{
        alert("Register successfully!");
        this.router.navigateByUrl('\customer-login');
      }
    }
    );

  }
}
