import { ShoppingCartService } from './../shopping-cart.service';
import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';
import { FoodDishService } from '../food-dish.service';

@Component({
  selector: 'app-shopping-cart-hovering',
  templateUrl: './shopping-cart-hovering.component.html',
  styleUrls: ['./shopping-cart-hovering.component.css']
})
export class ShoppingCartHoveringComponent implements OnInit {

  foodDishes: FoodDish[] = [];
  totalValue: number = 0;

  constructor(
    public webMessage: WebMessageService,
    private shoppingCartService: ShoppingCartService
  )
  {

  }

  ngOnInit(): void {
    this.getFoodDishesInShoppingCart();
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.webMessage.isCartVisible = false;
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.webMessage.isCartVisible = true;
    }else if(sessionStorage.getItem("loginStatus") == "2"){
      this.webMessage.isCartVisible = false;
    }
  }

  getFoodDishesInShoppingCart(): void{
    if(sessionStorage.getItem("userID") != null){
      this.shoppingCartService.getFoodDishesArray(Number(sessionStorage.getItem("userID"))).subscribe(dishes => {
        this.foodDishes = dishes;
        this.updateTotal();
      });
    }else{
      this.webMessage.isCartVisible = false;
    }
  }

  updateTotal(){
    var total = 0;
    for(var dish of this.foodDishes){
      total += dish.quantity * (dish.cost * 100);
    }
    this.totalValue = total/100;
  }
}
