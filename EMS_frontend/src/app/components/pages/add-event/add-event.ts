import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import Category from '../../../models/category.model';
import { CategoryService } from '../../../services/category-service';
import { EventService } from '../../../services/event-service';
import EventDetail from '../../../models/event-detail.model';
import { AuthService } from '../../../services/auth-service';
import { Toast } from "../../toast/toast";

@Component({
  selector: 'app-add-event',
  imports: [FormsModule, CommonModule, Toast],
  templateUrl: './add-event.html',
  styleUrl: './add-event.css',
})
export class AddEvent implements OnInit{
  organizerId!:number;

  allCategories: Category[] = [];
  minDate: string;  // for setting min attribute on date input

  showToast: boolean = false;
  toastMessage: string = 'Added new event successfully';

  newEvent: EventDetail = {
    eventId: 0,
    organizerId: 0,
    categoryId: 0,
    eventTitle: '',
    eventDescription: '',
    eventDate: '',
    startTime: '',
    endTime: '',
    location: '',
    capacity: 0,
    availableSeats: 0,
    artists: '',
    ageLimit: 0,
    price: 0,
    eventImage: '',
    organizer: undefined,
    eventCategory: {
      categoryId: 0,
      createdOrganizerId: 0,
      categoryName: '',
      createdBy: undefined,
      categoryEvents: undefined
    },
    eventAllRegistrations: [],
    categoryName: ''
  }

  constructor(private categoryService: CategoryService,private eventService: EventService,private authService:AuthService,private location:Location) {
    const today = new Date();
    this.minDate = today.toISOString().split('T')[0];  // yyyy-mm-dd

    this.loadCategories();

  }
  
  ngOnInit(): void {
    let userId = this.authService.retrieveUserInfo()?.userId;

    if(userId)
    {
      this.organizerId = userId;
    }
  }

  loadCategories() {
    this.categoryService.getAllCategories().subscribe({
      next:(response) => {
        console.log(response);
        this.allCategories = response;
      },
      error:(err) => console.log(err),
    })
  }


  handleSubmit(form:NgForm) {
    if (form.valid) {
      const formattedEvent = {
        ...this.newEvent,
        organizerId: this.organizerId,
        availableSeats: this.newEvent.capacity,
        eventDate: new Date(this.newEvent.eventDate).toISOString().split('T')[0], // yyyy-MM-dd
        startTime: this.convertTo24Hour(this.newEvent.startTime),
        endTime: this.convertTo24Hour(this.newEvent.endTime)
      };

      console.log('Creating event:', this.newEvent);


      this.eventService.addNewEvent(formattedEvent).subscribe({
        next: (response) => {
          console.log(response);
         

          this.showToast = true;
          setTimeout(() => {
            this.showToast = false;
            this.goBack();
          }, 2000);
        },
        error: (err) => console.log(err),
      })

      form.resetForm();
      
    }
  }

  

  convertTo24Hour(time: string): string {
    if (!time) return '';
    const [hourStr, minuteStr] = time.split(':');
    return `${hourStr.padStart(2, '0')}:${minuteStr.padStart(2, '0')}:00`; // HH:mm:ss
  }

  goBack() {
    this.location.back();
  }
}
