import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {SaleRequest} from "./sale-request";

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private baseUrl = 'http://localhost:8080/api/sales'; // change to your actual backend URL

  constructor(private http: HttpClient) {
  }

  createSale(SaleRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, SaleRequest);
  }

  getAllSales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  getSaleById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  getTotalSalesBetween(startDateTime: string, endDateTime: string): Observable<any> {
    const params = new HttpParams()
      .set('startDateTime', startDateTime)
      .set('endDateTime', endDateTime);
    return this.http.get(`${this.baseUrl}/total-between`, {params});
  }
}
