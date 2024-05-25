import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCommentDialogComponent } from './new-comment-dialog.component';

describe('NewCommentDialogComponent', () => {
  let component: NewCommentDialogComponent;
  let fixture: ComponentFixture<NewCommentDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewCommentDialogComponent]
    });
    fixture = TestBed.createComponent(NewCommentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
