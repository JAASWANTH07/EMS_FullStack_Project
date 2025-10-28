import { CommonModule, DatePipe, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {  RouterLink } from '@angular/router';
import Registration from '../../../models/registration.model';
import { UserService } from '../../../services/user-service';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-my-events',
  imports: [RouterLink,DatePipe,CommonModule],
  templateUrl: './my-events.html',
  styleUrl: './my-events.css',
})
export class MyEvents implements OnInit {
  userId!:number;

  allRegistrations:Registration[] = [];

  constructor(private userService:UserService,private authService:AuthService,private location:Location) {}

  goBack() {
    this.location.back();
  }

  
  ngOnInit(): void {
    this.loadAllDetails();
  };

  loadAllDetails()
  {
    let loginUserId = this.authService.retrieveUserInfo()?.userId;

    if(loginUserId)
    {
      this.userId = loginUserId;
    }

    this.userService.getAUser(this.userId).subscribe({
      next:(response) => {
        console.log(response);
        this.allRegistrations = response.registeredEvents.reverse();
      },
      error: (err) => console.log(err),
    })
  }

}

