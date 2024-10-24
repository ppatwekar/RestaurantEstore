import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryAddDishComponent } from './inventory-add-dish.component';

describe('InventoryAddDishComponent', () => {
  let component: InventoryAddDishComponent;
  let fixture: ComponentFixture<InventoryAddDishComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventoryAddDishComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryAddDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
