import { Component } from '@angular/core';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css'],
})
export class CreateProductComponent {
  product = {
    name: '',
    barcode: '',
    price: null,
    stock: null,
    available: true
  };

  successMessage = '';
  errorMessage = '';

  constructor(private productService: ProductService) {}

  createProduct(): void {
    if (!this.product.name || !this.product.barcode || this.product.price == null || this.product.stock == null) {
      this.errorMessage = 'All fields are required.';
      return;
    }

    this.productService.create(this.product).subscribe({
      next: () => {
        this.successMessage = '✅ Product created successfully!';
        this.errorMessage = '';
        this.product = { name: '', barcode: '', price: null, stock: null, available: true };
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Failed to create product.';
        this.successMessage = '';
      }
    });
  }
}

