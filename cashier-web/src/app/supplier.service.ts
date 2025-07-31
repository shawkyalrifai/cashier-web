import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

   private baseUrl = 'http://localhost:8080/api/suppliers'; 
 
   constructor(private http: HttpClient) {
   }
 
   private getHeaders(): HttpHeaders {
       const token = localStorage.getItem('token');
         return new HttpHeaders({
         'Authorization': `Bearer ${token}`
       });
     }
   createSupplier(supplier: any): Observable<any> {
     return this.http.post(`${this.baseUrl}`, supplier , { headers: this.getHeaders() });
   }
 
   getAllSupplier(): Observable<any[]> {
     return this.http.get<any[]>(`${this.baseUrl}` , { headers: this.getHeaders() });
   }
 
}
