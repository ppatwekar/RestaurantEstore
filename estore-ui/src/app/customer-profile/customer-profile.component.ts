import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { CustomerService } from '../customer.service';
import { Customer } from './../customer';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-customer-profile',
  templateUrl: './customer-profile.component.html',
  styleUrls: ['./customer-profile.component.css']
})
export class CustomerProfileComponent implements OnInit {

  public customer: Customer | undefined;

  profileForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
    firstname: new FormControl(''),
    lastname: new FormControl(''),
    email: new FormControl(''),
    phone: new FormControl(''),
    address: new FormControl(''),
  });

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private customerService: CustomerService
  )
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.loadCustomer();
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  loadCustomer(): void{
    if(sessionStorage.getItem("userID")==null){
      alert("Not log in yet!")
      return;
    }
    this.customerService.getCustomerByID(sessionStorage.getItem("userID") as string).subscribe(customer=>{
      this.customer = customer;
      this.profileForm.setValue({
        username: customer.username,
        password: customer.password,
        firstname: customer.firstName,
        lastname: customer.lastName,
        email: customer.email,
        phone: customer.phone,
        address: customer.address
      });
    })
  }

  updateCustomer(username: string, password: string, firstName: string, lastName: string, email: string, phone: string, address: string): void{
    if(username == "" || password == "" || firstName == "" || lastName == "" || email == "" || phone == "" || address == ""){
      alert("Please finish all input fields!");
      return;
    }
    const newCustomer = {
      id: Number(sessionStorage.getItem("userID")),
      username: username,
      password: password,
      firstName: firstName,
      lastName: lastName,
      email: email,
      phone: phone,
      address: address
    }
    this.customerService.updateCustomer(newCustomer as Customer).subscribe(customer=>{
      if(customer == null){
        alert("Update failed!");
      }else{
        alert("Profile updated!");
        this.router.navigateByUrl('\menu');
      }
    }
    );

  }
}
