import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { SupplierService } from '../supplier.service';
import { CategoryService } from '../category.service';

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
    available: true,
    supplierId: null,
    categoryId: null
  };
  selectedFile: File | null = null;
  successMessage = '';
  errorMessage = '';
  suppliers: any[] = [];
  categories: any[] = [];

  constructor(private productService: ProductService ,private supplierService: SupplierService, private categoryService: CategoryService) {}

  ngOnInit(): void {
  this.supplierService.getAllSupplier().subscribe(data => this.suppliers = data);
  this.categoryService.getAllCategory().subscribe(data => this.categories = data);
}
  onFileChange(event: Event): void {
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
    }
}
 createProduct(): void {
  if (!this.product.name || !this.product.barcode || this.product.price == null || this.product.stock == null || !this.selectedFile) {
    this.errorMessage = 'All fields are required, including the photo.';
    return;
  }
 const formData = new FormData();
  formData.append('name', this.product.name);
  formData.append('barcode', this.product.barcode);
  formData.append('price', this.product.price);
  formData.append('stock', this.product.stock);
  formData.append('available', String(this.product.available));
  formData.append('supplierId', String(this.product.supplierId ?? 0));
  formData.append('categoryId', String(this.product.categoryId ?? 0));
  formData.append('multipartFile', this.selectedFile); // match @RequestPart name
  

  this.productService.create(formData).subscribe({
    next: () => {
      this.successMessage = 'Product created successfully!';
      this.errorMessage = '';
      this.product = { name: '', barcode: '', price: null, stock: null, available: true , supplierId:null , categoryId:null };
      this.selectedFile = null;
    },
    error: (err) => {
      this.errorMessage = err.error?.message || 'Failed to create product.';
      this.successMessage = '';
    }
  });
}
}
