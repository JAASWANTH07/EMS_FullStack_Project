import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import EventDetail from '../models/event-detail.model';
import Registration from '../models/registration.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  baseUrl: string = `http://${window.location.hostname}/api/ems/events`;

  constructor(private httpClient:HttpClient) {};

  getAllEvents(): Observable<EventDetail[]> {
    return this.httpClient.get<EventDetail[]>(this.baseUrl);
  }

  getAEvent(eventId:Number): Observable<EventDetail> {
    return this.httpClient.get<EventDetail>(`${this.baseUrl}/${eventId}`);
  }

  addNewEvent(newEvent:EventDetail): Observable<EventDetail> {
    return this.httpClient.post<EventDetail>(`${this.baseUrl}/add`,newEvent);
  }

  updateEvent(editEvent:EventDetail): Observable<EventDetail> {
    return this.httpClient.put<EventDetail>(`${this.baseUrl}/update`,editEvent);
  }

  regsterForEvent(userId:Number,eventId:Number,totalTickets:Number): Observable<Registration> {
    return this.httpClient.get<Registration>(`${this.baseUrl}/register/${userId}/${eventId}/${totalTickets}`)
  }

  getAllLocations(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.baseUrl}/locations`);
  }

  getAllUpcomingEvents(): Observable<EventDetail[]> {
    return this.httpClient.get<EventDetail[]>(`${this.baseUrl}/upcoming`);
  }
}
