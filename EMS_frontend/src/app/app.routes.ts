import { Routes } from '@angular/router';
import { Home } from './components/pages/home/home';
import { EventDetails } from './components/event/event-details/event-details';
import { EventsList } from './components/event/events-list/events-list';
import { MyEvents } from './components/pages/my-events/my-events';
import { OrganizerDashboard } from './components/pages/organizer-dashboard/organizer-dashboard';
import { ViewEvents } from './components/pages/view-events/view-events';
import { AddEvent } from './components/pages/add-event/add-event';
import { AddCategory } from './components/pages/add-category/add-category';
import { EditEvent } from './components/pages/edit-event/edit-event';
import { Login } from './components/pages/login/login';
import { Register } from './components/pages/register/register';
import { authGuard } from './gaurds/auth-guard';
import { Profile } from './components/pages/profile/profile';


export const routes: Routes = [
    {
        path:'',
        redirectTo: 'login',
        pathMatch: 'full',
    },
    {
        path:'login',
        component: Login
    },
    {
        path:'signup',
        component: Register
    },
    {
        path:'home',
        component: Home,
        children: [
            {
                path:'',
                component: EventsList,
            },
            {
                path:'event-details/:eventId',
                component:EventDetails,
                canActivate: [authGuard],
            },
            {
                path:'my-events',
                component: MyEvents,
                canActivate: [authGuard],
            },
            {
                path:'profile',
                component: Profile,
                canActivate: [authGuard],
            },
            
        ]
    },
    {
        path:'organizer-dashboard',
        component:OrganizerDashboard,
        children: [
            {
                path:'view-events',
                component:ViewEvents,
                canActivate: [authGuard],
            },
            {
                path:'add-event',
                component: AddEvent,
                canActivate: [authGuard],
            },
            {
                path:'add-category',
                component: AddCategory,
                canActivate: [authGuard],
            },
            {
                path:'edit-event/:eventId',
                component: EditEvent,
                canActivate: [authGuard],
            },
            {
                path:'profile',
                component: Profile,
                canActivate: [authGuard],
            },

        ]
    }
];
