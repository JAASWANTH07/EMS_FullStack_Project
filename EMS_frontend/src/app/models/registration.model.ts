
import EventDetail from "./event-detail.model";
import User from "./user.model";

interface Registration {
    registrationId: number;
    userId: number;
    eventId: number;
    totalTickets: number;
    registrationDate: string;   
    status: string;
    totalPrice: number;
    registeredUser: User;                
    registeredEventDetails: EventDetail;
}

export default Registration;