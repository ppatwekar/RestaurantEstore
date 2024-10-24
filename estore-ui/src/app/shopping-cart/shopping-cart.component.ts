import { OrderService } from './../order.service';
import { ShoppingCartItemDataFormat } from './../shopping-cart-item-data';
import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';
import { FoodDishService } from '../food-dish.service';
import { ShoppingCartService } from '../shopping-cart.service';
import { Order } from '../order';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  foodDishes: FoodDish[] = [];
  totalValue: number = 0;

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private shoppingCartService: ShoppingCartService,
    private orderService: OrderService
  )
  {

  }

  ngOnInit(): void {
    this.getFoodDishesInShoppingCart();
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  getFoodDishesInShoppingCart(): void{
    if(sessionStorage.getItem("userID") != null){
      this.shoppingCartService.getFoodDishesArray(Number(sessionStorage.getItem("userID"))).subscribe(dishes => {
        this.foodDishes = dishes;
        this.updateTotal();
      });
    }else{
      alert("Need log in!");
    }
  }

  increaseQuantity(dishId: number, currQuantity: number){
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      var newQuantity = currQuantity + 1;
      const request = {
        foodDishId: dishId,
        quantity: newQuantity
      }
      this.shoppingCartService.updateShoppingCart(request as ShoppingCartItemDataFormat, Number(sessionStorage.getItem("userID"))).subscribe(result=>{
        if(result == null){
          alert("Operation failed!");
        }else{
          this.foodDishes = result;
          this.updateTotal();
        }
      }
      );
    }
  }

  decreaseQuantity(dishId: number, currQuantity: number){
    if(currQuantity == 0){
      return;
    }
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      var newQuantity = currQuantity - 1;
      const request = {
        foodDishId: dishId,
        quantity: newQuantity
      }
      this.shoppingCartService.updateShoppingCart(request as ShoppingCartItemDataFormat, Number(sessionStorage.getItem("userID"))).subscribe(result=>{
        if(result == null){
          alert("Operation failed!");
        }else{
          this.foodDishes = result;
          this.updateTotal();
        }
      }
      );
    }
  }

  removeDish(dishId: number){
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      var newQuantity = 0;
      const request = {
        foodDishId: dishId,
        quantity: newQuantity
      }

      this.shoppingCartService.updateShoppingCart(request as ShoppingCartItemDataFormat, Number(sessionStorage.getItem("userID"))).subscribe(result=>{
        if(result == null){
          alert("Operation failed!");
        }else{
          this.foodDishes = result;
          this.updateTotal();
        }
      }
      );
    }
  }

  updateTotal(){
    var total = 0;
    for(var dish of this.foodDishes){
      total += dish.quantity * (dish.cost * 100);
    }
    this.totalValue = total/100;
  }

  checkOut(){
    //generate a order
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      const request = {
        customerId: Number(sessionStorage.getItem("userID")),
        dishes: this.foodDishes
      }
      if(this.foodDishes.length == 0){
        alert("Your shopping cart is empty!");
        return;
      }
      this.orderService.createOrder(request as Order).subscribe(result=>{
        if(result == null){
          alert("Operation failed!");
        }else{
          alert("Order Generated!");
          this.router.navigateByUrl('\order-overview');
        }
      }
      );
    }
  }
}
