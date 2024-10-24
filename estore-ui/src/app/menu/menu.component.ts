import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';
import { FoodDishService } from '../food-dish.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

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
    if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
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

  navigateToCustomizePage(){
    if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }else{
      this.router.navigateByUrl('\customize-dish');
    }
  }
}
