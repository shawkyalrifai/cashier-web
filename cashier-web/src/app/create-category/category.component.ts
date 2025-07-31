import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {

categoryForm: FormGroup;

successMessage = '';
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    this.categoryForm = this.fb.group({
      name: ['', Validators.required]
    });
  }
  onCreateCategory(): void {
    if (this.categoryForm.invalid) return;

    this.categoryService.createCategory(this.categoryForm.value).subscribe({
      next: () => {
        this.successMessage = 'Category created successfully!';
        this.errorMessage = '';
        this.categoryForm.reset();
        // this.loadCategories(); 
      },
      error: () => {
        this.successMessage = '';
        this.errorMessage = 'Error creating category.';
      }
    });
}
}