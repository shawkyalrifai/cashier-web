import { Component, OnInit } from '@angular/core';

import { Route, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import {AuthService} from "../auth.service";
import { BehaviorSubject } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  isLoggedIn = false;
  username = '';
  role: string | null = null;
  constructor(private authService: AuthService, private router: Router,private translate: TranslateService) {
    translate.setDefaultLang('en');
  }


ngOnInit(): void {
  // for translate 
  const lang = localStorage.getItem('lang') || 'en';
  this.translate.use(lang);
  document.documentElement.dir = lang === 'ar' ? 'rtl' : 'ltr';


  this.authService.isLoggedIn$.subscribe(status => {
    console.log('🔍 isLoggedIn:', status);
    this.isLoggedIn = status;

    if (status) {
      const user = JSON.parse(localStorage.getItem('user') || '{}');
      this.username = user.username || '';
      this.role = user.role || '';
      console.log('🔍 user:', user);
    } else {
      this.username = '';
      this.role = '';
    }
  });

  const savedLang = localStorage.getItem('lang') || 'en';
  this.translate.use(savedLang);
  document.documentElement.dir = savedLang === 'ar' ? 'rtl' : 'ltr';
}


   switchLang(lang: string) {
  this.translate.use(lang);
  localStorage.setItem('lang', lang);
  document.documentElement.dir = lang === 'ar' ? 'rtl' : 'ltr';
}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
    localStorage.clear();
    this.role = null;
    this.isLoggedIn = false;
  }
   isDashboardPage(): boolean {
    return this.router.url === '/dashboard';  
  }
}