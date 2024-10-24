import { OrderService } from './../order.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';
import { Order } from '../order';

@Component({
  selector: 'app-order-overview',
  templateUrl: './order-overview.component.html',
  styleUrls: ['./order-overview.component.css']
})
export class OrderOverviewComponent implements OnInit {

  orders : Order[] = [];

  constructor(
    private router: Router,
    private orderService: OrderService
  )
  {

  }

  ngOnInit(): void {
    this.getOrders(); //Uncomment after back-end done
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  getOrders(): void{
    if(sessionStorage.getItem("userID") != null){
      this.orderService.getOrders(Number(sessionStorage.getItem("userID"))).subscribe(orders => {
        this.orders = orders;
      });
    }else{
      alert("Need log in!");
    }
  }

  goToOrderDetail(id: number): void{
    const url = `/order-detail/${id}`;
    this.router.navigateByUrl(url);
  }

}
