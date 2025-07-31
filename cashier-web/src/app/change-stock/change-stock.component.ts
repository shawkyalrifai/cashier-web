import { Component } from '@angular/core';
import { Stock } from '../stock';
import { StockService } from '../stock.service';

@Component({
  selector: 'app-change-stock',
  templateUrl: './change-stock.component.html',
  styleUrls: ['./change-stock.component.css']
})
export class ChangeStockComponent {
  reasons = [
  { label: 'Restock', value: 'Restock' },
  { label: 'Sale', value: 'Sale' },
  { label: 'Correction', value: 'Correction' }
];
log: Stock = {
    productId: 0,
    quantityChanged: 0,
    reason: '',
    changeDate: new Date().toISOString()
  };
  message = '';

  constructor(private stockService: StockService) {}

  submitLog() {
    this.stockService.create(this.log).subscribe({
      next: (res) => {
        this.message = 'Stock log created successfully';
        console.log(res);
      },
      error: (err) => {
        this.message = 'Error creating stock log';
        console.error(err);
      }
    });
  }
}

