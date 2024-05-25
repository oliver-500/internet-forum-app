import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmLoginPageComponent } from './confirm-login-page.component';

describe('ConfirmLoginPageComponent', () => {
  let component: ConfirmLoginPageComponent;
  let fixture: ComponentFixture<ConfirmLoginPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmLoginPageComponent]
    });
    fixture = TestBed.createComponent(ConfirmLoginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
