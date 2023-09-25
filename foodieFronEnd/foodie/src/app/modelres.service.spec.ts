import { TestBed } from '@angular/core/testing';

import { ModelresService } from './modelres.service';

describe('ModelresService', () => {
  let service: ModelresService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModelresService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
