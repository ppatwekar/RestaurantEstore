import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import {Admin} from './admin'

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private adminUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  getAdminByUsernameAndPassword(username: string, password: string): Observable<Admin>{
    const url = `${this.adminUrl}/?username=${username}&password=${password}`;
    return this.http.get<Admin>(url).pipe(
      catchError(this.handleError<Admin>(`getAdmin username=${username}`))
    );
  }

  getAdminByID(id: string): Observable<Admin>{
    const url = `${this.adminUrl}/${id}`;
    return this.http.get<Admin>(url).pipe(
      catchError(this.handleError<Admin>(`getAdmin id=${id}`))
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
