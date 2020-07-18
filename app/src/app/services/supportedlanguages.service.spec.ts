import { TestBed } from '@angular/core/testing';

import { SupportedlanguagesService } from './supportedlanguages.service';

describe('SupportedlanguagesService', () => {
  let service: SupportedlanguagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SupportedlanguagesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
