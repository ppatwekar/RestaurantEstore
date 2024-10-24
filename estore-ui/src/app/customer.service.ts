import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import {Customer} from './customer'

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customerUrl = 'http://localhost:8080/customer';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getCustomerByUsernameAndPassword(username: string, password: string): Observable<Customer>{
    const url = `${this.customerUrl}/?username=${username}&password=${password}`;
    return this.http.get<Customer>(url).pipe(
      catchError(this.handleError<Customer>(`getCustomer username=${username}`))
    );
  }

  getCustomerByID(id: string): Observable<Customer>{
    const url = `${this.customerUrl}/${id}`;
    return this.http.get<Customer>(url).pipe(
      catchError(this.handleError<Customer>(`getCustomer id=${id}`))
    );
  }

  updateCustomer(customer: Customer): Observable<any>{
    return this.http.put(this.customerUrl, customer, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateCustomer'))
    );
  }

  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.customerUrl, customer, this.httpOptions).pipe(
      catchError(this.handleError<Customer>('addCustomer'))
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
