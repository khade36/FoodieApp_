import { TestBed } from '@angular/core/testing';

import { RestoItemServiceService } from './resto-item-service.service';

describe('RestoItemServiceService', () => {
  let service: RestoItemServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RestoItemServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
