import { Component } from '@angular/core';
import {ProductService} from "../product.service";
import {SaleService} from "../sale.service";
import html2pdf from 'html2pdf.js';
import { Route, Router } from '@angular/router';
@Component({
  selector: 'app-create-sale',
  templateUrl: './create-sale.component.html',
  styleUrls: ['./create-sale.component.css']
})
export class CreateSaleComponent {

  products: any[] = [];
  cashierId: number = 1;
  bill: any = null; // 👈 for showing the bill
  error: string = '';
   username = '';
  constructor(
    private productService: ProductService,
    private saleService: SaleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.getAll().subscribe({
      next: (data) => {
        this.products = data.map(p => ({ ...p, quantity: 0 }));
      },
      error: () => alert("Failed to load products")
    });
     this.username = localStorage.getItem('username') || '';
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
      next: (response) => {
        this.bill = response; 
        this.error = '';
      
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

