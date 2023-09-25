import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteRestoComponent } from './favourite-resto.component';

describe('FavouriteRestoComponent', () => {
  let component: FavouriteRestoComponent;
  let fixture: ComponentFixture<FavouriteRestoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FavouriteRestoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavouriteRestoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
