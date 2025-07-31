import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SupplierService } from '../supplier.service';

@Component({
  selector: 'app-supplier',
  templateUrl: './supplier.component.html',
  styleUrls: ['./supplier.component.css']
})
export class SupplierComponent {
  supplierForm: FormGroup ;
  supplierMessage = '';

  constructor(
    private fb: FormBuilder,
    private supplierService: SupplierService,

  ) {
    this.supplierForm = this.fb.group({
      name: ['', Validators.required],
      phone: ['']
    });
  }
   onCreateSupplier(): void {
    if (this.supplierForm.valid) {
      this.supplierService.createSupplier(this.supplierForm.value).subscribe({
        next: () => this.supplierMessage = 'Supplier created successfully!',
        error: () => this.supplierMessage = 'Error creating supplier.'
      });
    }
  }

}