import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

 registerForm!: FormGroup;
 constructor ( private fb: FormBuilder ,private router: Router){

 }


  ngOnInit(): void {
    this.registerForm = this.fb.group({
         firstname:['',[Validators.required]],
         lastname:['',[Validators.required]],
         email:['',[Validators.required, Validators.email]],
         password:['',[Validators.required]],

    },{Validators:this.passwordMathValidator})
  }
  passwordMathValidator(formGroup:FormGroup){
    const password = formGroup.get('password')?.value;
  }




  goToLoginList(){
    this.router.navigate(['/login']);
  }
}

