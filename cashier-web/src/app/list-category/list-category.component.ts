import { Component, OnInit } from '@angular/core';
import { Category } from '../category';
import { FormBuilder } from '@angular/forms';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-list-category',
  templateUrl: './list-category.component.html',
  styleUrls: ['./list-category.component.css']
})
export class ListCategoryComponent implements OnInit {
categories: Category[] = [];
  successMessage = '';
  errorMessage = '';


  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService
  ) {
    
  }
ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.categoryService.getAllCategory().subscribe({
      next: (data) => this.categories = data,
      error: () => this.errorMessage = 'Failed to load categories.'
    });
  }
}