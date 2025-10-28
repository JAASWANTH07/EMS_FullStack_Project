import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import User from '../../../models/user.model';
import { UserService } from '../../../services/user-service';
import { AuthService } from '../../../services/auth-service';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  errorMessage: string = '';

  loginUser: User = {
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

  constructor(private userService: UserService,private authService:AuthService,private router:Router) {};

  handleLogin(form: NgForm) {
    if (form.valid) {
      console.log('Logging in with:', this.loginUser);

      this.userService.loginUser(this.loginUser).subscribe({
        next:(response) => {
          console.log(response);
          console.log("Login Successfull!!!");

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
        error:(err) => {
          console.log(err);
          this.errorMessage = 'Username or Password is invalid'
        },
      })

      form.resetForm();
    }
}

}
