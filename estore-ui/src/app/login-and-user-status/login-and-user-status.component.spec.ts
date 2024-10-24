import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginAndUserStatusComponent } from './login-and-user-status.component';

describe('LoginAndUserStatusComponent', () => {
  let component: LoginAndUserStatusComponent;
  let fixture: ComponentFixture<LoginAndUserStatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginAndUserStatusComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginAndUserStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
