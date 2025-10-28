import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterOutlet } from '@angular/router';
import { UserService } from '../../../services/user-service';
import User from '../../../models/user.model';
import { AuthService } from '../../../services/auth-service';

@Component({
  selector: 'app-organizer-dashboard',
  imports: [RouterOutlet,RouterLink],
  templateUrl: './organizer-dashboard.html',
  styleUrl: './organizer-dashboard.css',
})
export class OrganizerDashboard implements OnInit{

  organizer?: User;

  totalEvents!:number;
  totalAttendees!:number;
  totalRevenue!:number;
  totalActiveCategories:number = 2;
  totalCategoryId!:number;

  constructor(private router:Router,private userService:UserService,private authService:AuthService) {};
  
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
          this.loadAllStats();
        },
        error: (err) => console.log(err),
      })
    }
    
  }

  loadAllStats() {
    if (this.organizer) {
      this.totalEvents = this.organizer.organizedEvents.length;
      this.totalAttendees = 0;
      this.totalRevenue = 0;
  
      for (let event of this.organizer.organizedEvents) {
        const attendees = event.capacity - event.availableSeats;
        this.totalAttendees += attendees;
        this.totalRevenue += event.price * attendees;
      }
  
      this.totalActiveCategories = this.getUniqueCategoryCount();
    }
  }

  getUniqueCategoryCount(): number {
    if (!this.organizer) return 0;
  
    const uniqueCategories = new Set<number>();
  
    for (let event of this.organizer.organizedEvents) {
      if (event.categoryId) {
        uniqueCategories.add(event.categoryId);
      }
    }
  
    return uniqueCategories.size;
  }
  
  

  showDashboard():boolean {
    return ['/organizer-dashboard'].includes(this.router.url);
  }

  handleViewEvents() {
    this.router.navigate(['/organizer-dashboard/view-events']);
  }

  handleAddEvent() {
    this.router.navigate(['/organizer-dashboard/add-event']);
  }

  handleAddCategory() {
    this.router.navigate(['/organizer-dashboard/add-category']);
  }


  handleLogout()
  {
    this.authService.deleteToken();

    this.authService.deleteUserInfo();

    this.authService.isLoggedIn = false;

    this.router.navigate(['/login'])
  }

}
