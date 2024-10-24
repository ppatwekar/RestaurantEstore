import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuDishDetailComponent } from './menu-dish-detail.component';

describe('MenuDishDetailComponent', () => {
  let component: MenuDishDetailComponent;
  let fixture: ComponentFixture<MenuDishDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuDishDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MenuDishDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
