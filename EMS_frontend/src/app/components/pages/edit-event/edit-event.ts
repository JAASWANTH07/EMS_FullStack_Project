import { CommonModule, Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import EventDetail from '../../../models/event-detail.model';
import Category from '../../../models/category.model';
import { EventService } from '../../../services/event-service';
import { CategoryService } from '../../../services/category-service';
import { AuthService } from '../../../services/auth-service';
import { Toast } from "../../toast/toast";

@Component({
  selector: 'app-edit-event',
  imports: [FormsModule, CommonModule, Toast],
  templateUrl: './edit-event.html',
  styleUrl: './edit-event.css',
})
export class EditEvent implements OnInit{

  showToast: boolean = false;
  toastMessage: string = 'Updated the event successfully';

  event: EventDetail = {
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
    eventCategory: {
      categoryId: 0,
      createdOrganizerId: 0,
      categoryName: ''
    },
    categoryName: ''
  };  

  minDate: string;

  allCategories:Category[] = [];

  constructor(private activatedRoute: ActivatedRoute,private eventService:EventService,private categoryService:CategoryService,private authService:AuthService,private location:Location) {
    const today = new Date();
    this.minDate = today.toISOString().split('T')[0];
  }

  ngOnInit() {
  
    let eventId = this.activatedRoute.snapshot.paramMap.get('eventId');

    if(eventId)
    {
      this.eventService.getAEvent(parseInt(eventId)).subscribe({
        next:(response) => {
          console.log(response);
          this.event = response;
        },
        error: (err) => console.log(err),
      });

      this.categoryService.getAllCategories().subscribe({
        next: (response) => {
          console.log(response);
          this.allCategories = response;
        },
        error:(err) => console.log(err),
      });


    }
  }

  handleSubmit(form:NgForm) {
    if (form.valid) {

      const formattedEvent = {
        ...this.event,
        availableSeats: this.event.capacity,
        eventDate: new Date(this.event.eventDate).toISOString().split('T')[0], // yyyy-MM-dd
        startTime: this.convertTo24Hour(this.event.startTime),
        endTime: this.convertTo24Hour(this.event.endTime)
      };

      console.log('Updated event:', formattedEvent);

      this.eventService.updateEvent(formattedEvent).subscribe({
        next:(response) => {
          console.log(response);
          
          this.showToast = true;
          setTimeout(() => {
            this.showToast = false;
            this.goBack();
          }, 2000);
        },
        error:(err) => console.log(err),
      });

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


