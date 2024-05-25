import { TestBed } from '@angular/core/testing';

import { UseraccessesService } from './useraccesses.service';

describe('UseraccessesService', () => {
  let service: UseraccessesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UseraccessesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
