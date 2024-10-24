import { TestBed } from '@angular/core/testing';

import { WebMessageService } from './web-message.service';

describe('WebMessageService', () => {
  let service: WebMessageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WebMessageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
