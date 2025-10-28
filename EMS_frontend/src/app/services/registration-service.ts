import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  baseUrl: string = `http://${window.location.hostname}/api/ems/registrations`;
}
