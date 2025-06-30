import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../auth.service";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  error = '';


  constructor(private authService: AuthService, private router: Router) {}
login(): void {
  this.authService.login({ username: this.username, password: this.password }).subscribe({
    next: (res) => {
      if (!res?.token) {
        this.error = 'Login failed: no token';
        return;
      }

      this.authService.loginSuccess(res.token); // ✅ pass token
      this.router.navigate(['/dashboard']);
    },
    error: () => {
      this.error = 'Invalid username or password';
    }
  });
}
}