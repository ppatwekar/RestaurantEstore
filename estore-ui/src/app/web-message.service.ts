import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WebMessageService {

  title: string = 'Rice Food Service';
  isCartVisible: boolean = false;

  constructor() {

  }
}
