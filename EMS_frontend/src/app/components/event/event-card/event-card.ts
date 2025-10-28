import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import EventDetail from '../../../models/event-detail.model';

@Component({
  selector: 'app-event-card',
  imports: [DatePipe],
  templateUrl: './event-card.html',
  styleUrl: './event-card.css',
})
export class EventCard {
  @Input() event?:EventDetail;

  @Output() viewEventDetails = new EventEmitter<Number> ();

  handleViewDetails(eventId?:Number) {
    this.viewEventDetails.emit(eventId);
  }
}
