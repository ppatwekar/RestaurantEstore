import { ShoppingCartService } from './../shopping-cart.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FoodDish } from '../food-dish';

@Component({
  selector: 'app-customize-dish',
  templateUrl: './customize-dish.component.html',
  styleUrls: ['./customize-dish.component.css']
})
export class CustomizeDishComponent implements OnInit {

  addQuantity: number = 1;
  price: number = 9.99;//Fixed price

  constructor(
    private router: Router,
    private shoppingCartService: ShoppingCartService
  )
  {

  }

  ngOnInit(): void {
    if(sessionStorage.getItem("loginStatus") == "2"){
      this.router.navigateByUrl('\inventory');
    }
  }

  increaseQuantity(): void{
    this.addQuantity += 1; // Increasing the Quantity.
  }

  decreaseQuantity(): void{
    if(this.addQuantity > 1){
      this.addQuantity -= 1; // Decreasing the Quantity.
    }
  }

  addCustomizedDish(rice: string, topping: string): void{
    if(sessionStorage.getItem("loginStatus") == "0" || sessionStorage.getItem("loginStatus") == null){
      this.router.navigateByUrl('\customer-login');
    }else{
      //add to cart if customer logged in

      var content = rice + " + " + topping;//Put the content of the customized dish here

      const request = {
        name: "Customized Dish",
        cost: this.price,
        quantity: this.addQuantity,
        recipe: content,
        description: "This is a customized dish.",
        imageurl: "../assets/dishImages/customizedDish.jpg"
      }
      this.shoppingCartService.addCustomizedDish(request as FoodDish, Number(sessionStorage.getItem("userID"))).subscribe(result=>{
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
