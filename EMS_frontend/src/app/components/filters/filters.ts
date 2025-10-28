import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import Category from '../../models/category.model';
import { CategoryService } from '../../services/category-service';
import { EventService } from '../../services/event-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filters',
  imports: [FormsModule],
  templateUrl: './filters.html',
  styleUrl: './filters.css',
})
export class Filters implements OnInit{
  allCategories: Category[] = [];
  allLocations: string[] = [];

  currentCategory:string = 'All Categories';
  currentLocation:string = 'All Locations';
  currentDate:string = '';

  @Output() filterDetails = new EventEmitter<{category:string,location:string,eventDate: string}>();

  constructor(private categoryService:CategoryService,private eventService:EventService) {}
  
  ngOnInit(): void {
    this.loadInfo();
  };

  loadInfo() {
    this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        console.log(response);
        this.allCategories = response;
      },
      error: (err) => console.log(err),
    });

    this.eventService.getAllLocations().subscribe({
      next:(response) => {
        console.log(response);
        this.allLocations = response;
      },
      error: (err) => console.log(err),
    })


  }

  handleApplyFilters()
  {
    
    this.filterDetails.emit({
      category: this.currentCategory,
      location: this.currentLocation,
      eventDate: this.currentDate
    })
  }


}
