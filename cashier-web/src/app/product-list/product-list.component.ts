import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';

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

  constructor(private productService: ProductService, private router:Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }
 getImageSrc(photo: Uint8Array): string | null {
  if (!photo || photo.length === 0) return null;

  try {
    const base64String = btoa(
      Array.from(photo).map(byte => String.fromCharCode(byte)).join('')
    );
    return `data:image/jpeg;base64,${base64String}`;
  } catch (e) {
    console.error('Error converting image:', e);
    return null;
  }
}

  loadProducts(): void {
    this.productService.getAll().subscribe({
      next: data => this.products = data,
      error: err => this.error = 'Failed to load products'
    });
  }

  search(): void {
    this.error = ''; 
  
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
          this.products = [];
          this.error = err.error?.message || 'Product not found';
        } else {
          this.error = 'Search failed. Please try again.';
        }
      }
    });
  }
 viewProductDetails(productId: number): void {
    this.router.navigate(['/product', productId]);
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

