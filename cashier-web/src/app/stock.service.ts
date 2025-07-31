import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Stock } from './stock';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  private baseUrl = 'http://localhost:8080/api/stock';

  constructor(private http: HttpClient) {}


   private getHeaders(): HttpHeaders {
      const token = localStorage.getItem('token');
        return new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
    }
  create(log: Stock): Observable<Stock> {
    return this.http.post<Stock>(this.baseUrl, log , { headers: this.getHeaders() });
  }

  getById(id: number): Observable<Stock> {
    return this.http.get<Stock>(`${this.baseUrl}/${id}` , { headers: this.getHeaders() });
  }

  update(id: number, log: Stock): Observable<Stock> {
    return this.http.put<Stock>(`${this.baseUrl}/${id}`, log , { headers: this.getHeaders() });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}` , { headers: this.getHeaders() });
  }

  search(keyword: string): Observable<Stock> {
    return this.http.get<Stock>(`${this.baseUrl}/search?keyword=${keyword}` , { headers: this.getHeaders() });
  }
}
