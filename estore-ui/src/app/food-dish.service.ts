import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { FoodDish } from './food-dish';

@Injectable({
  providedIn: 'root'
})
export class FoodDishService {
  private dishesUrl = 'http://localhost:8080/dish';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET dishes from the server */
  getFoodDishes(): Observable<FoodDish[]> {
    return this.http.get<FoodDish[]>(this.dishesUrl)
      .pipe(
        catchError(this.handleError<FoodDish[]>('getFoodDishes', []))
      );
  }

  /** GET dish by id. Return `undefined` when id not found */
  getFoodDishNo404<Data>(id: number): Observable<FoodDish> {
    const url = `${this.dishesUrl}/?id=${id}`;
    return this.http.get<FoodDish[]>(url)
      .pipe(
        map(dishes => dishes[0]), // returns a {0|1} element array
        catchError(this.handleError<FoodDish>(`getFoodDish id=${id}`))
      );
  }

  /** GET dish by id. Will 404 if id not found */
  getFoodDish(id: number): Observable<FoodDish> {
    const url = `${this.dishesUrl}/${id}`;
    return this.http.get<FoodDish>(url).pipe(
      catchError(this.handleError<FoodDish>(`getFoodDish id=${id}`))
    );
  }

  /* GET dishes whose name contains search term */
  searchFoodDishes(term: string): Observable<FoodDish[]> {
    if (!term.trim()) {
      // if not search term, return empty dish array.
      return of([]);
    }
    return this.http.get<FoodDish[]>(`${this.dishesUrl}/?name=${term}`).pipe(
      catchError(this.handleError<FoodDish[]>('searchFoodDishes', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new dish to the server */
  addFoodDish(dish: FoodDish): Observable<FoodDish> {
    return this.http.post<FoodDish>(this.dishesUrl, dish, this.httpOptions).pipe(
      catchError(this.handleError<FoodDish>('addFoodDish'))
    );
  }

  /** DELETE: delete the dish from the server */
  deleteFoodDish(id: number): Observable<FoodDish> {
    const url = `${this.dishesUrl}/${id}`;

    return this.http.delete<FoodDish>(url, this.httpOptions).pipe(
      catchError(this.handleError<FoodDish>('deleteFoodDish'))
    );
  }

  /** PUT: update the dish on the server */
  updateFoodDish(dish: FoodDish): Observable<any> {
    return this.http.put(this.dishesUrl, dish, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateFoodDish'))
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
