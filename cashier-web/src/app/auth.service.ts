import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {AuthResponse} from "./auth-response";
import {AuthRequest} from "./auth-request";
import { DecodedToken } from './decoded-token';
import { jwtDecode } from 'jwt-decode'; 


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080/api/auth';
 private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.isLoggedInSubject.asObservable();
  
  constructor(private http: HttpClient) {
  }

  login(credentials: { username: string; password: string }): Observable<any> {
  return this.http.post<any>('http://localhost:8080/api/auth/login', credentials).pipe(
    tap(response => {
      localStorage.setItem('token', response.token);
      localStorage.setItem('username', credentials.username); 
    })
  );
}
  // login(credentials: { username: string; password: string }): Observable<any> {
  //   return this.http.post<any>('http://localhost:8080/api/auth/login', credentials).pipe(
  //     tap(response => {
  //       localStorage.setItem('token', response.token);
  //     })
  //   );
  // }
   loginSuccess(token: string): void {
    const decoded: DecodedToken = jwtDecode(token);
    const user = {
      username: decoded.sub,
      role: decoded.roles?.[0] || ''
    };

    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('username', user.username);

    this.isLoggedInSubject.next(true);
  }
  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  logout(): void {
    localStorage.clear();
    this.isLoggedInSubject.next(false);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  
  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }
}
