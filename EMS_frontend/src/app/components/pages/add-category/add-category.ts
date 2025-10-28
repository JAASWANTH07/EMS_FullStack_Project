import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import Category from '../../../models/category.model';
import { CategoryService } from '../../../services/category-service';
import { AuthService } from '../../../services/auth-service';
import { Toast } from "../../toast/toast";

@Component({
  selector: 'app-add-category',
  imports: [FormsModule, CommonModule, Toast],
  templateUrl: './add-category.html',
  styleUrl: './add-category.css',
})
export class AddCategory implements OnInit{
  organizerId!:number;

  showToast: boolean = false;
  toastMessage: string = 'Added new category successfully';

  newCategory: Category = {
    categoryId: 0,
    createdOrganizerId: 0,
    categoryName: ''
  };

  constructor(private categoryService: CategoryService,private authService:AuthService,private location:Location) {};

  ngOnInit(): void {
    let userId = this.authService.retrieveUserInfo()?.userId;

    if(userId)
    {
      this.organizerId = userId;
    }
  }
;

  handleSubmit(form:NgForm): void {
    if (form.valid) {
     this.newCategory.createdOrganizerId = this.organizerId;
      console.log('Adding new category:', this.newCategory);

      this.categoryService.addNewCategory(this.newCategory).subscribe({
        next: (response) => {
          console.log(response);

          this.showToast = true;
          setTimeout(() => {
            this.showToast = false;
            this.goBack();
          }, 2000);

          form.resetForm();
        },
        error: (err) => {
          console.error(err);
          if (err.status === 500 && err.error?.message?.includes("Unique")) {
            alert("Category name already exists. Please choose another name.");
          } else {
            alert("Something went wrong while creating the category!");
          }
        }
      });
      
    }
  }

  goBack() {
    this.location.back();
  }
}
