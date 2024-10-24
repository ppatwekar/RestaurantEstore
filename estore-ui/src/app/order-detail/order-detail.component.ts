import { OrderService } from './../order.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FoodDish } from '../food-dish';
import { Order } from '../order';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {

  orderId : number = 0;
  foodDishes: FoodDish[] = [];
  totalValue: number = 0;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private orderService: OrderService
  )
  {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.getFoodDishesInOrder(); //Uncomment after back-end done
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  getFoodDishesInOrder(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.orderService.getOrderByID(id).subscribe(order => {
      if(order == null || order.customerId != Number(sessionStorage.getItem("userID"))){
        this.router.navigateByUrl('\order-overview');
      }

      this.orderId = order.id;
      this.foodDishes = order.dishes;
      this.updateTotal();
    })
  }

  deleteOrder(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.orderService.deleteOrderByID(id).subscribe(order => {
      this.router.navigateByUrl('\order-overview');
    })
  }

  updateTotal(){
    var total = 0;
    for(var dish of this.foodDishes){
      total += dish.quantity * (dish.cost * 100);
    }
    this.totalValue = total/100;
  }
}
