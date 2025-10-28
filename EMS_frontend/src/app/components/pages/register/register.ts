import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import User from '../../../models/user.model';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../../services/user-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-register',
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  newUser:User = {
    userId: 0,
    userName: '',
    email: '',
    password: '',
    phone: '',
    allRoles: [],
    createdCategories: [],
    organizedEvents: [],
    registeredEvents: []
  };
 
  constructor(private router:Router,private userService:UserService,private authService:AuthService) {};

  handleRegister(form: NgForm, type: 'organizer' | 'participant') {
    if (form.valid) {

      if(type === 'organizer')
      {
        this.newUser.allRoles = [
          {
            roleId: 1,
            roleName: 'Organizer'
          }
        ]
      }
      else if(type === 'participant')
      {
        this.newUser.allRoles = [
          {
            roleId: 2,
            roleName: 'Participant'
          }
        ]
      }

      this.userService.registerUser(this.newUser).subscribe({
        next: (response) => {
          console.log(response);
          console.log("Registration Successfull!!!");

          this.authService.storeToken(response.token);

          this.authService.storeUserInfo(response.user);
          
          this.authService.isLoggedIn = true;

          if(this.authService.isOrganizer())
          {
            this.router.navigate(['/organizer-dashboard']);
          }
          else if(this.authService.isParticipant())
          {
            this.router.navigate(['/home']);
          }
        },
        error: (err) => console.log(err),
      })
      
      form.resetForm();
    }
  }
}
