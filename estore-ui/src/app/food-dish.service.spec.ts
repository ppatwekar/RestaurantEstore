import { TestBed } from '@angular/core/testing';

import { FoodDishService } from './food-dish.service';

describe('FoodDishService', () => {
  let service: FoodDishService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FoodDishService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
