import { Component } from '@angular/core';
import {ProductService} from "../product.service";
import {SaleService} from "../sale.service";

@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent {
  products: any[] = [];
  cashierId: number = 1; // Optional: Replace with actual user ID from auth

  constructor(
    private productService: ProductService,
    private saleService: SaleService
  ) {}

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data.map(p => ({ ...p, quantity: 0 }));
      },
      error: () => alert("Failed to load products")
    });
  }

  submitSale(): void {
    const selectedItems = this.products
      .filter(p => p.quantity > 0 && p.stock > 0)
      .map(p => ({
        productId: p.id,
        quantity: p.quantity
      }));
  
    if (selectedItems.length === 0) {
      alert("Please select at least one product with quantity.");
      return;
    }
  
    const saleRequest = {
      cashierId: this.cashierId,
      items: selectedItems
    };
  
    this.saleService.createSale(saleRequest).subscribe({
      next: () => {
        alert("Sale created successfully!");
  
        // Decrease local stock
        this.products.forEach(product => {
          const soldItem = selectedItems.find(item => item.productId === product.id);
          if (soldItem) {
            product.stock -= soldItem.quantity;
            product.quantity = 0; // Reset quantity input
          }
        });
      },
      error: () => {
        alert("Error creating sale.");
      }
    });
  }
}