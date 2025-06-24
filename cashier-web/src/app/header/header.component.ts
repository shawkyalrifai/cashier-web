import { Component, OnInit } from '@angular/core';

import { Route, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import {AuthService} from "../auth.service";
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent{
  //  jwtToken = localStorage.getItem('jwt');
  // token!:string ;
  static jwtToken: any;
    isLoggedIn = false;

    constructor(private authService: AuthService) {
      this.authService.isLoggedIn$.subscribe(status => {
        this.isLoggedIn = status;
      });
    }
}
