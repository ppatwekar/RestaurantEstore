import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomizeDishComponent } from './customize-dish.component';

describe('CustomizeDishComponent', () => {
  let component: CustomizeDishComponent;
  let fixture: ComponentFixture<CustomizeDishComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomizeDishComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomizeDishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
