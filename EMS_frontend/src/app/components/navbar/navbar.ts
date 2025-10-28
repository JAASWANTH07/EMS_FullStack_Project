import { Component, HostListener, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { SearchService } from '../../services/search-service';
import { FormsModule } from '@angular/forms';
import User from '../../models/user.model';
import { AuthService } from '../../services/auth-service';
import { UserService } from '../../services/user-service';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink,FormsModule,RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit{
  organizer?: User;

  searchQuery: string = '';
  
  constructor(private router:Router,private searchService:SearchService,private authService:AuthService,private userService:UserService) {}
  
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
        },
        error: (err) => console.log(err),
      })
    }
    
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 20) {
      navbar?.classList.add('scrolled');
    } else {
      navbar?.classList.remove('scrolled');
    }
  }

  showSearchBar():boolean {
    return ['/home'].includes(this.router.url);
  }

  handleSearch() {
    this.searchService.updateSearchQuery(this.searchQuery.trim());
  }

  logout() {
    console.log('Logout triggered');
    
    this.authService.deleteToken();

    this.authService.deleteUserInfo();

    this.authService.isLoggedIn = false;

    this.router.navigate(['/login']);
  }

}
