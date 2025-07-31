import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private baseUrl = 'http://localhost:8080/api/category'; 
 
   constructor(private http: HttpClient) {
   }
 
   private getHeaders(): HttpHeaders {
       const token = localStorage.getItem('token');
         return new HttpHeaders({
         'Authorization': `Bearer ${token}`
       });
     }
   createCategory(category: any): Observable<any> {
     return this.http.post(`${this.baseUrl}`, category , { headers: this.getHeaders() });
   }
 
   getAllCategory(): Observable<any[]> {
     return this.http.get<any[]>(`${this.baseUrl}` , { headers: this.getHeaders() });
   }
  }