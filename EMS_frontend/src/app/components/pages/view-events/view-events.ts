import { CommonModule, CurrencyPipe, DatePipe, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import User from '../../../models/user.model';
import { UserService } from '../../../services/user-service';
import EventDetail from '../../../models/event-detail.model';
import { Router, RouterLink, RouterOutlet } from "@angular/router";
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-view-events',
  imports: [CommonModule, DatePipe, RouterLink, RouterOutlet],
  templateUrl: './view-events.html',
  styleUrl: './view-events.css',
})
export class ViewEvents implements OnInit{

  organizer?: User;
  organizerAllEvents: EventDetail[] = [];

  constructor(private userService:UserService,private router:Router,private authService:AuthService,private location:Location) {}
  
  ngOnInit(): void {
   this.loadUserDetails();
  };

  loadUserDetails() {
    let organizerId = this.authService.retrieveUserInfo()?.userId;

    if(organizerId)
    {
      this.userService.getAUser(organizerId).subscribe({
        next: (response) => {
          console.log(response);
          this.organizer = response;
          this.organizerAllEvents = this.organizer.organizedEvents.reverse();
        },
        error: (err) => console.log(err),
      })
    }
  }

  getEventStatus(eventDate: any): string {
    const today = new Date();
    const start = new Date(eventDate);

    if (today < start) {
      return 'Upcoming';
    } else if (today == start) {
      return 'Ongoing';
    } else {
      return 'Completed';
    }
  }

  getRemainingDaysForEvent(eventDate: any): number | string {
    const today = new Date();
    const eventDateObj = new Date(eventDate); // convert string → Date
  
    if (today <= eventDateObj) {
      const diffInMs = eventDateObj.getTime() - today.getTime();
      const diffInDays = Math.ceil(diffInMs / (1000 * 60 * 60 * 24));
      return diffInDays;
    }
  
    return 'Event Completed';
  }
  
  showEvents():boolean {
    return ['/organizer-dashboard/view-events'].includes(this.router.url);
  }


  goBack() {
    this.location.back();
  }
 
}
