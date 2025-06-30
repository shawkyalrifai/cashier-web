import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {SaleRequest} from "./sale-request";

@Injectable({
  providedIn: 'root'
})
export class SaleService {

  private baseUrl = 'http://localhost:8080/api/sales'; // change to your actual backend URL

  constructor(private http: HttpClient) {
  }

  private getHeaders(): HttpHeaders {
      const token = localStorage.getItem('token');
        return new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    }
  createSale(SaleRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}`, SaleRequest , { headers: this.getHeaders() });
  }

  getAllSales(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}` , { headers: this.getHeaders() });
  }

  getSaleById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}` , { headers: this.getHeaders() });
  }

  getTotalSalesBetween(startDateTime: string, endDateTime: string): Observable<any> {
  const params = new HttpParams()
    .set('startDateTime', startDateTime)
    .set('endDateTime', endDateTime);

  return this.http.get(`${this.baseUrl}/total-between`, {
    headers: this.getHeaders(),
    params: params
  });
}

}
