import { Component, OnInit } from '@angular/core';
import { EventCard } from "../event-card/event-card";
import { CommonModule } from '@angular/common';
import { Filters } from "../../filters/filters";
import { ActivatedRoute, Router, RouterOutlet } from '@angular/router';
import { EventService } from '../../../services/event-service';
import EventDetail from '../../../models/event-detail.model';
import { Subscription } from 'rxjs/internal/Subscription';
import { SearchService } from '../../../services/search-service';


@Component({
  selector: 'app-events-list',
  imports: [EventCard, CommonModule, Filters, RouterOutlet],
  templateUrl: './events-list.html',
  styleUrl: './events-list.css',
})
export class EventsList implements OnInit{
  allEvents: EventDetail[] = [];
  originalAllEvents: EventDetail[] = [];

  currentCategory:string = 'All Categories';
  currentLocation:string = 'All Locations';
  currentDate:string = '';
  currentSearchQuery:string = '';

  searchSubscription!: Subscription;

  constructor(private router:Router,private eventService: EventService,private activatedRoute:ActivatedRoute,private searchService:SearchService) {}

  ngOnInit(): void {
    this.loadEvents();

    this.searchSubscription = this.searchService.currentSearchQuery.subscribe(
      (query) => {
        this.currentSearchQuery = query.toLowerCase();
        this.applyFilters();
      }
    );
  };

  loadEvents() {
    this.eventService.getAllUpcomingEvents().subscribe({
      next: (response) => {
        console.log(response);
        this.allEvents = response;
        this.originalAllEvents = response;
      },
      error: (err) => console.log(err),
    })
  }


  showEventsList():boolean {
    return ['/home'].includes(this.router.url);
  }

  handleViewDetails(eventId:Number)
  {
    console.log(eventId);
    this.router.navigate(['event-details',eventId],{
      relativeTo: this.activatedRoute
    });
  }

  handleFilterDetails(response:{category:string,location:string,eventDate:string}) {
    this.currentCategory = response.category;
    this.currentLocation = response.location;
    this.currentDate = response.eventDate;

    console.log(this.currentCategory);
    console.log(this.currentLocation);
    console.log(this.currentDate);

    this.applyFilters();

  }

  applyFilters() {
    console.log(this.currentSearchQuery);

    let filteredEvents = [...this.originalAllEvents];

    if(this.currentSearchQuery && this.currentSearchQuery.trim() !== '')
    {
      filteredEvents = filteredEvents.filter((event) => 
        event.eventTitle.toLowerCase().includes(this.currentSearchQuery.toLowerCase())
      );
    }

    if(this.currentCategory !== 'All Categories')
    {
      filteredEvents = filteredEvents.filter((event) => 
        event.categoryName === this.currentCategory
      )
    }

    if(this.currentLocation !== 'All Locations')
    {
      filteredEvents = filteredEvents.filter((event) => 
        event.location === this.currentLocation
      )
    }

    if (this.currentDate !== '') {
      filteredEvents = filteredEvents.filter((event) => {
        const eventDate = new Date(event.eventDate).toISOString().split('T')[0];
        const filterDate = new Date(this.currentDate).toISOString().split('T')[0];
        return eventDate === filterDate;
      });
    }

    this.allEvents = filteredEvents;
    
  }

  ngOnDestroy() {
    if (this.searchSubscription) this.searchSubscription.unsubscribe();
  }
}
