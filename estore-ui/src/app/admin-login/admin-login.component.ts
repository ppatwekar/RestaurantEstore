import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private adminService: AdminService
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

  adminLogin(username: string, password: string): void{
    this.adminService.getAdminByUsernameAndPassword(username,password).subscribe(admin =>{
      if(admin == null){
        alert("Wrong username or password!");
      }else{
        sessionStorage.setItem("loginStatus", "2");
        sessionStorage.setItem("loginInfo", `Hey! ${admin.username}`);
        sessionStorage.setItem("userID", admin.id.toString());
        this.router.navigateByUrl('\inventory');
      }}
    );
  }

}
