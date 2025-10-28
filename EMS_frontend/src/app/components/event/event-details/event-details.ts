import { Component, OnInit } from '@angular/core';
import EventDetail from '../../../models/event-detail.model';
import { EventService } from '../../../services/event-service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule, CurrencyPipe, DatePipe, Location } from '@angular/common';
import { AuthService } from '../../../services/auth-service';
import { Toast } from "../../toast/toast";

@Component({
  selector: 'app-event-details',
  imports: [CurrencyPipe, DatePipe, CommonModule, Toast],
  templateUrl: './event-details.html',
  styleUrl: './event-details.css',
})
export class EventDetails implements OnInit {
  userId!:number;
  event?: EventDetail;

  isModalOpen = false;
  ticketCount = 1;
  errorText = '';

  showToast: boolean = false;
  toastMessage: string = 'Registered for the event successfully';

  constructor(private eventService:EventService,private activatedRoute: ActivatedRoute,private authService:AuthService,private location:Location) {};
  
  ngOnInit(): void {
    let loginUser = this.authService.retrieveUserInfo()?.userId;

    if(loginUser)
    {
      this.userId = loginUser;
    }

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
    }
    
  }

  goBack() {
    this.location.back();
  }

  get startDate(): Date | null {
    return this.event?.startTime ? new Date(`1970-01-01T${this.event.startTime}`) : null;
  }

  get endDate(): Date | null {
    return this.event?.endTime ? new Date(`1970-01-01T${this.event.endTime}`) : null;
  };


  openModal() { this.isModalOpen = true; this.errorText = ''; this.ticketCount = 1; }
  closeModal() { this.isModalOpen = false; }
  increment() { 
    if(this.ticketCount+1 <= 10)
    {
      if(this.event?.availableSeats)
      {
        if(this.ticketCount+1 <= this.event.availableSeats)
        {
          this.ticketCount++;
        }
        else 
        {
          this.errorText = "Only " + this.event.availableSeats + " are left!!!";
          setTimeout(() => {
            this.errorText = '';
          }, 1000);
        }
      }
    }
    else{
      this.errorText="Max 10 tickets is only allowed for a user!!!";
      setTimeout(() => {
        this.errorText = '';
      }, 1000);
    }
  } 
  decrement() { 
    if(this.ticketCount-1 > 0)
    {
      this.ticketCount--;
    }
  } 
  registerTickets() { 
    console.log(this.ticketCount);

    if(this.event?.eventId)
    {
      this.eventService.regsterForEvent(this.userId,this.event.eventId,this.ticketCount).subscribe({
        next:(response) => {
          console.log(response);
          console.log("Registered for the event successfully!!!");

          this.showToast = true;
          setTimeout(() => {
            this.showToast = false;
            this.goBack();
          }, 2000);

        },
        error: (err) => console.log(err),
      })
    }

    this.closeModal();

  } 

}
