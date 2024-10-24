import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { FoodDishService } from './../food-dish.service';
import { FoodDish } from './../food-dish';

@Component({
  selector: 'app-inventory-add-dish',
  templateUrl: './inventory-add-dish.component.html',
  styleUrls: ['./inventory-add-dish.component.css']
})
export class InventoryAddDishComponent implements OnInit {

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private foodDishService: FoodDishService
  )
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\admin-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\menu');
    }
  }

  createFoodDish(name: string, cost: string, quantity: string, recipe: string, description: string, imageurl: string): void{
    if(name == "" || cost == "" || quantity == "" || recipe == "" || description == "" || imageurl == ""){
      alert("Please finish all input fields!");
      return;
    }
    const newDish = {
      name: name,
      cost: Number(cost),
      quantity: Number(quantity),
      recipe: recipe,
      description:description,
      imageurl: imageurl
    }
    if(Number(cost) < 0){
      alert("Please set a cost that is equal or bigger than 0!");
      return;
    }
    if(Number(quantity) < 0){
      alert("Please set a quantity that is equal or bigger than 0!");
      return;
    }
    this.foodDishService.addFoodDish(newDish as FoodDish).subscribe(dish=>{
      if(dish == null){
        alert("Food dish already exists!");
      }else{
        alert("Add food dish successfully!");
        this.router.navigateByUrl('\inventory');
      }
    }
    );
  }
}
