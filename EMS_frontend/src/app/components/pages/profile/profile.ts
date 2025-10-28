import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import User from '../../../models/user.model';
import { UserService } from '../../../services/user-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, CommonModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit{
  userProfile:User = {
    userId: 0,
    userName: '',
    email: '',
    password: '',
    phone: '',
    allRoles: [],
    createdCategories: [],
    organizedEvents: [],
    registeredEvents: []
  }
 

 // State for modal and edit fields
 showEditModal: boolean = false;
 

 constructor(private userService:UserService,private authService:AuthService,private location:Location) {}
 
 ngOnInit(): void {
    this.loadUserProfile();
  };

  loadUserProfile() {
    let userId = this.authService.retrieveUserInfo()?.userId;

    if(userId)
    {
      this.userService.getAUser(userId).subscribe({
        next: (response) => {
          console.log(response);
          this.userProfile = response;
        }
      })    
    }
  }

  goBack() {
    this.location.back();
  }

 // Open modal and load current values
 openEditModal() {
   this.showEditModal = true;
  
 }

 // Close modal
 closeEditModal() {
   this.showEditModal = false;
 }

 // Save profile changes
 saveProfile() {
   // Add real validation and save logic here as needed.

 }
}
