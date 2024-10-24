import { ShoppingCartItemDataFormat } from './../shopping-cart-item-data';
import { ShoppingCartService } from './../shopping-cart.service';
import { FoodDishService } from './../food-dish.service';
import { FoodDish } from './../food-dish';
import { Component, OnInit } from '@angular/core';
import { WebMessageService } from '../web-message.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-menu-dish-detail',
  templateUrl: './menu-dish-detail.component.html',
  styleUrls: ['./menu-dish-detail.component.css']
})
export class MenuDishDetailComponent implements OnInit {

  dish: FoodDish | undefined;
  addQuantity: number = 1;

  constructor(
    public webMessage: WebMessageService,
    private router: Router,
    private route: ActivatedRoute,
    private foodDishService: FoodDishService,
    private shoppingCartService: ShoppingCartService
  )
  {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit(): void {
    this.getFoodDish();
    if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  getFoodDish(): void{
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    if(Number(id) < 0){
      this.router.navigateByUrl('\customize-dish');
      return;
    }
    this.foodDishService.getFoodDish(id).subscribe(dish => {
      this.dish = dish;
      if(this.dish == null){
        this.router.navigateByUrl('\menu');
      }
    });
  }

  increaseQuantity(inventoryQuantity: number): void{
    if(this.addQuantity + 1 <= inventoryQuantity){
      this.addQuantity += 1; // Increasing the Quantity.
    }
  }

  decreaseQuantity(): void{
    if(this.addQuantity > 1){
      this.addQuantity -= 1; // Decreasing the Quantity.
    }
  }

  addToCart(): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      //add to cart if customer logged in
      const request = {
        foodDishId: this.dish?.id,
        quantity: this.addQuantity
      }
      this.shoppingCartService.addShoppingCart(request as ShoppingCartItemDataFormat, Number(sessionStorage.getItem("userID"))).subscribe(result=>{
        if(result == null){
          alert("Operation failed!");
        }else{
          this.router.navigateByUrl('\shopping-cart');
        }
      }
      );

    }
  }
}
