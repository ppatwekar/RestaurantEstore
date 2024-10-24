import { Order } from './order';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderUrl = 'http://localhost:8080/order';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getOrderByID(id: number): Observable<Order>{
    const url = `${this.orderUrl}/${id}`;
    return this.http.get<Order>(url).pipe(
      catchError(this.handleError<Order>(`getOrder id=${id}`))
    );
  }

  getOrders(customerId: number): Observable<Order[]>{
    const url = `${this.orderUrl}/customer/${customerId}`;
    return this.http.get<Order[]>(url).pipe(
      catchError(this.handleError<Order[]>(`getOrder customerId=${customerId}`))
    );
  }

  createOrder(order: Order): Observable<Order> {
    const url = `${this.orderUrl}/createOrder`;
    return this.http.put<Order>(url, order, this.httpOptions).pipe(
      catchError(this.handleError<Order>('createOrder'))
    );
  }

  deleteOrderByID(id: number): Observable<Order> {
    const url = `${this.orderUrl}/${id}`;

    return this.http.delete<Order>(url, this.httpOptions).pipe(
      catchError(this.handleError<Order>('deleteOrder'))
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
