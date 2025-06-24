import { Component, OnInit } from '@angular/core';
import { SaleService } from '../sale.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-list-sale',
  templateUrl: './list-sale.component.html',
  styleUrls: ['./list-sale.component.css'],
})
export class ListSaleComponent implements OnInit{
  sales: any[] = [];
  selectedSale: any = null;
  totalSales: number | null = null;
  startDate: string = '';
  endDate: string = '';
  saleId: number | null = null;
  error = '';
  constructor(private saleService: SaleService) {}

  ngOnInit(): void {
    this.getAllSales();
  }

  getAllSales(): void {
    this.saleService.getAllSales().subscribe(
      data => this.sales = data,
      err => this.error = 'Failed to fetch sales.'
    );
  }

  getSaleById(): void {
    if (!this.saleId) {
      alert('Please enter a sale ID.');
      return;
    }

    this.saleService.getSaleById(this.saleId).subscribe({
      next: data => this.selectedSale = data,
      error: () => {
        this.selectedSale = null;
        alert('Sale not found.');
      }
    });
  }

  getTotalSalesBetween(): void {
    if (!this.startDate || !this.endDate) {
      alert('Please select both start and end dates.');
      return;
    }

    const start = `${this.startDate}T00:00:00`;
    const end = `${this.endDate}T23:59:59`;

    this.saleService.getTotalSalesBetween(start, end).subscribe({
      next: data => this.totalSales = data.total,
      error: () => alert('Failed to fetch total sales.')
    });
  }

}
