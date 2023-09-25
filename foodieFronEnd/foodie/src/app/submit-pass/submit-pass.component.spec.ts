import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitPassComponent } from './submit-pass.component';

describe('SubmitPassComponent', () => {
  let component: SubmitPassComponent;
  let fixture: ComponentFixture<SubmitPassComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitPassComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmitPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
