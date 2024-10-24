import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoMidComponent } from './logo-mid.component';

describe('LogoMidComponent', () => {
  let component: LogoMidComponent;
  let fixture: ComponentFixture<LogoMidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogoMidComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogoMidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
