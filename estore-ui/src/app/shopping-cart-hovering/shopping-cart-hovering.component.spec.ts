import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShoppingCartHoveringComponent } from './shopping-cart-hovering.component';

describe('ShoppingCartHoveringComponent', () => {
  let component: ShoppingCartHoveringComponent;
  let fixture: ComponentFixture<ShoppingCartHoveringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShoppingCartHoveringComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShoppingCartHoveringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
