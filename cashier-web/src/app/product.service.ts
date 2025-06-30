import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {
  }
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }

  getAll(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl, { headers: this.getHeaders() });
  }

  getById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }

  create(productFormData: FormData): Observable<any> {
  return this.http.post(`${this.baseUrl}/create`, productFormData,{headers: this.getHeaders()});
  }

  update(id: number, product: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, product, { headers: this.getHeaders() });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers: this.getHeaders() });
  }

  search(keyword: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/search?keyword=${keyword}`, { headers: this.getHeaders() });
  }
}