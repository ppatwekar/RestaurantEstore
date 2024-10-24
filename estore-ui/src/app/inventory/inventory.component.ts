import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';
import { FoodDishService } from '../food-dish.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  foodDishes: FoodDish[] = [];

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private foodDishService: FoodDishService
  )
  {

  }

  ngOnInit(): void {
    this.getFoodDishes();
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\admin-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\menu');
    }
  }

  getFoodDishes(): void{
    this.foodDishService.getFoodDishes().subscribe(dishes => this.foodDishes = dishes);
  }

  getFoodDishesBySearch(term: string): void{
    if(term == "" || term == null){
      this.getFoodDishes();
    }else{
      this.foodDishService.searchFoodDishes(term).subscribe(dishes => {
        this.foodDishes = dishes;
        if(this.foodDishes.length == 0){
          alert("Can't find any matching!")
          this.getFoodDishes();
        }
      });
    }
  }

  goToAddFoodDish(): void{
    this.router.navigateByUrl('\inventory-add-dish');
  }
}
