import { TestBed } from '@angular/core/testing';

import { ConfirmationGuardService } from './confirmation-guard.service';

describe('ConfirmationGuardService', () => {
  let service: ConfirmationGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfirmationGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
