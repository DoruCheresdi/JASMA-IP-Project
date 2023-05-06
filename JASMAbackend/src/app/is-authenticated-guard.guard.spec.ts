import { TestBed } from '@angular/core/testing';

import { IsAuthenticatedGuardGuard } from './is-authenticated-guard.guard';

describe('IsAuthenticatedGuardGuard', () => {
  let guard: IsAuthenticatedGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IsAuthenticatedGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
