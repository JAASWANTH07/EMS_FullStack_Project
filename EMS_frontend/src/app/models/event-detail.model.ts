import Category from "./category.model";
import Registration from "./registration.model";
import User from "./user.model";

interface EventDetail {
  eventId: number;
  organizerId: number;
  categoryId: number;
  eventTitle: string;
  eventDescription: string;
  eventDate: string;     
  startTime: string;   
  endTime: string;      
  location: string;
  capacity: number;
  availableSeats: number;
  artists: string;
  ageLimit: number;
  price: number;
  eventImage: string;
  categoryName?: string;
  organizer?: User;                 
  eventCategory: Category;        
  eventAllRegistrations?: Registration[];
}

export default EventDetail;