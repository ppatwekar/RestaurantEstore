import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { FoodDish } from './food-dish';
import { ShoppingCartItemDataFormat } from './shopping-cart-item-data'

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  private cartUrl = 'http://localhost:8080/shoppingcart';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  //Get dishes in shopping cart by customer id
  getFoodDishesArray(customerId: number): Observable<FoodDish[]> {
    const url = `${this.cartUrl}/${customerId}/foodDishesArray`;
    return this.http.get<FoodDish[]>(url)
      .pipe(
        catchError(this.handleError<FoodDish[]>('getFoodDishesArray', []))
      );
  }

  //Edit dish in shopping cart by customer id
  updateShoppingCart(body: ShoppingCartItemDataFormat, customerId: number): Observable<any> {
    const url = `${this.cartUrl}/${customerId}/foodDishesArray`;
    return this.http.put(url, body, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateFoodDish'))
    );
  }

  //Add dish in shopping cart by customer id
  addShoppingCart(body: ShoppingCartItemDataFormat, customerId: number): Observable<any> {
    const url = `${this.cartUrl}/${customerId}/foodDishesAdd`;
    return this.http.put(url, body, this.httpOptions).pipe(
      catchError(this.handleError<any>('addShoppingCart'))
    );
  }

  addCustomizedDish(body: FoodDish, customerId: number): Observable<any>{
    const url = `${this.cartUrl}/${customerId}/customizedDishesAdd`;
    return this.http.put(url, body, this.httpOptions).pipe(
      catchError(this.handleError<any>('addCustomizedDish'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
   private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console

      return of(result as T);
    };
  }
}
