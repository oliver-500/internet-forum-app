import { TestBed } from '@angular/core/testing';

import { UserPanelGuardService } from './user-panel-guard.service';

describe('UserPanelGuardService', () => {
  let service: UserPanelGuardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserPanelGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
