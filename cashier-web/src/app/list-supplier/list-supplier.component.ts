import { Component, OnInit } from '@angular/core';
import { SupplierService } from '../supplier.service';

@Component({
  selector: 'app-list-supplier',
  templateUrl: './list-supplier.component.html',
  styleUrls: ['./list-supplier.component.css']
})
export class ListSupplierComponent implements OnInit{

  suppliers: any[] = [];
  errorMessage = '';

  constructor(private supplierService: SupplierService) {}

  ngOnInit(): void {
    this.loadSuppliers();
  }

  loadSuppliers(): void {
    this.supplierService.getAllSupplier().subscribe({
      next: (data) => this.suppliers = data,
      error: () => this.errorMessage = 'Failed to load suppliers.'
    });
  }
}
