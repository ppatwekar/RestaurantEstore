import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryDishDetailComponent } from './inventory-dish-detail.component';

describe('InventoryDishDetailComponent', () => {
  let component: InventoryDishDetailComponent;
  let fixture: ComponentFixture<InventoryDishDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InventoryDishDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryDishDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
