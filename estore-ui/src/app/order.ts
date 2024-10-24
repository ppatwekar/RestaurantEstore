import { FoodDish } from './food-dish';
export interface Order{
  id: number;
  customerId: number;
  dishes: FoodDish[];
}
