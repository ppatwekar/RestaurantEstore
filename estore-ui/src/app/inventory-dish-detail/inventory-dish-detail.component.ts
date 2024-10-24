import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { FoodDishService } from './../food-dish.service';
import { FoodDish } from './../food-dish';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-inventory-dish-detail',
  templateUrl: './inventory-dish-detail.component.html',
  styleUrls: ['./inventory-dish-detail.component.css']
})
export class InventoryDishDetailComponent implements OnInit {

  dish: FoodDish | undefined;

  profileForm = new FormGroup({
    name: new FormControl(''),
    cost: new FormControl(''),
    quantity: new FormControl(''),
    recipe: new FormControl(''),
    description: new FormControl(''),
    imageurl: new FormControl('')
  });

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private route: ActivatedRoute,
    private foodDishService: FoodDishService
  )
  {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.getFoodDish();
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\admin-login');
    }else if(sessionStorage.getItem("loginStatus") == "1"){
      this.router.navigateByUrl('\menu');
    }
  }

  getFoodDish(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.foodDishService.getFoodDish(id).subscribe(dish => {
      this.dish = dish;
      if(this.dish == null){
        this.router.navigateByUrl('\inventory');
      }
      this.profileForm.setValue({
        name: dish.name,
        cost: dish.cost.toString(),
        quantity: dish.quantity.toString(),
        recipe: dish.recipe,
        description: dish.description,
        imageurl: dish.imageurl
      });
    });
  }

  updateFoodDish(name: string, cost: string, quantity: string, recipe: string, description: string, imageurl: string): void{
    if(name == "" || cost == "" || quantity == "" || recipe == "" || description == "" || imageurl == ""){
      alert("Please finish all input fields!");
      return;
    }
    if(Number(cost) < 0){
      alert("Please set a cost that is equal or bigger than 0!");
      return;
    }
    if(Number(quantity) < 0){
      alert("Please set a quantity that is equal or bigger than 0!");
      return;
    }
    const newDish = {
      id: this.dish?.id,
      name: name,
      cost: Number(cost),
      quantity: Number(quantity),
      recipe: recipe,
      description:description,
      imageurl: imageurl
    }
    this.foodDishService.updateFoodDish(newDish as FoodDish).subscribe(dish=>{
      if(dish == null){
        alert("Update failed!");
      }else{
        alert("Dish updated!");
        this.router.navigateByUrl('\inventory');
      }
    }
    );
  }

  deleteFoodDish(): void{
    if(this.dish != null){
      this.foodDishService.deleteFoodDish(this.dish.id).subscribe(_=>{
        alert("Dish deleted!");
        this.router.navigateByUrl('\inventory');
      });
    }
  }
}
