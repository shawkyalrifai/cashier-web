import { Component } from '@angular/core';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
  products: any[] = [];
  keyword: string = '';
  selectedProduct: any = {
    id: null,
    name: '',
    price: 0
  };
  isEditing = false;
  error = '';

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAll().subscribe({
      next: data => this.products = data,
      error: err => this.error = 'Failed to load products'
    });
  }

  search(): void {
    this.error = ''; // clear previous error
  
    if (!this.keyword) {
      this.loadProducts();
      return;
    }
  
    this.productService.search(this.keyword).subscribe({
      next: data => {
        this.products = [data]; // wrap single product in an array
        this.error = ''; // clear error if search successful
      },
      error: err => {
        if (err.status === 404) {
          this.products = []; // clear old products
          this.error = err.error?.message || 'Product not found';
        } else {
          this.error = 'Search failed. Please try again.';
        }
      }
    });
  }

  save(): void {
    if (this.isEditing) {
      this.productService.update(this.selectedProduct.id, this.selectedProduct).subscribe({
        next: () => {
          this.loadProducts();
          this.resetForm();
        },
        error: err => this.error = 'Update failed'
      });
    } else {
      this.productService.create(this.selectedProduct).subscribe({
        next: () => {
          this.loadProducts();
          this.resetForm();
        },
        error: err => this.error = 'Create failed'
      });
    }
  }

  edit(product: any): void {
    this.selectedProduct = { ...product };
    this.isEditing = true;
  }

  delete(id: number): void {
    if (confirm('Are you sure to delete this product?')) {
      this.productService.delete(id).subscribe({
        next: () => this.loadProducts(),
        error: err => this.error = 'Delete failed'
      });
    }
  }

  resetForm(): void {
    this.selectedProduct = { id: null, name: '', price: 0 };
    this.isEditing = false;
    this.error = '';
  }
}

