import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeStockComponent } from './change-stock.component';

describe('ChangeStockComponent', () => {
  let component: ChangeStockComponent;
  let fixture: ComponentFixture<ChangeStockComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChangeStockComponent]
    });
    fixture = TestBed.createComponent(ChangeStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
