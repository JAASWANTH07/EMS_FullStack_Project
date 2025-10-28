
import Category from "./category.model";
import EventDetail from "./event-detail.model";
import Registration from "./registration.model";
import Role from "./role.model";

interface User {
    userId: number;
    userName: string;
    email: string;
    password: string;
    phone: string;
    allRoles: Role[];                // User can have multiple roles
    createdCategories: Category[];   // Categories created by user
    organizedEvents: EventDetail[];  // Events organized by user
    registeredEvents: Registration[];
}

export default User;