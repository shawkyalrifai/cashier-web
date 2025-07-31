import { Component } from '@angular/core';
import {ProductService} from "../product.service";
import {SaleService} from "../sale.service";
import html2pdf from 'html2pdf.js';
import { Route, Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { SaleRequest } from '../sale-request';
@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent {

  products: any[] = [];
  cashierUsername: string = '';
  customerPhone = '';
  customerName = '';
  bill: any = null;
  error: string = '';
   username = '';
  constructor(
    private productService: ProductService,
    private saleService: SaleService,
      private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
  this.productService.getAll().subscribe({
    next: (data) => {
      this.products = data.map(p => ({ ...p, quantity: 0 }));
    },
    error: () => alert("Failed to load products")
  });

  const userData = localStorage.getItem('user');
  this.username = userData ? JSON.parse(userData).username : '';
}

submitSale(): void {
  if (!this.customerName || this.customerName.trim().length < 3) {
    alert("Please enter a valid customer name (at least 3 characters).");
    return;
  }

  if (!this.customerPhone || !/^01[0-9]{9}$/.test(this.customerPhone)) {
    alert("Please enter a valid Egyptian phone number (11 digits, starts with 01).");
    return;
  }

  const selectedItems = this.products
    .filter(p => p.quantity > 0 && p.stock > 0)
    .map(p => ({
      productId: p.id,
      quantity: p.quantity,
      subtotal: p.subtotal
    }));

  if (selectedItems.length === 0) {
    alert("Please select at least one product with quantity.");
    return;
  }

  const saleRequest: SaleRequest = {
    customerPhone: this.customerPhone,
    customerName: this.customerName,
    items: selectedItems
  };

  this.saleService.createSale(saleRequest).subscribe({
    next: (response) => {
      this.bill = response;
      this.error = '';

      // clear quantities and update stock
      this.products.forEach(product => {
        const soldItem = selectedItems.find(item => item.productId === product.id);
        if (soldItem) {
          product.stock -= soldItem.quantity;
          product.quantity = 0;
        }
      });
    },
    error: () => {
      this.error = 'Error creating sale.';
      this.bill = null;
    }
  });
}


   downloadPDF(): void {
    const element = document.getElementById('bill-section');
    if (!element) return;

    const options = {
      filename: 'sale-receipt.pdf',
      image: { type: 'jpeg', quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };

    html2pdf().from(element).set(options).save();
  }

  printPDF(): void {
    const element = document.getElementById('bill-section');
    if (!element) return;

    const options = {
      filename: 'sale-receipt.pdf',
      image: { type: 'jpeg', quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    };

    html2pdf().from(element).set(options).outputPdf('bloburl').then((pdfUrl: string) => {
      const iframe = document.createElement('iframe');
      iframe.style.display = 'none';
      iframe.src = pdfUrl;
      document.body.appendChild(iframe);
      iframe.onload = () => setTimeout(() => iframe.contentWindow?.print(), 500);
    });
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

 viewProductDetails(productId: number): void {
    this.router.navigate(['/product', productId]);
  }

}

