import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  baseUrl: string = `http://${window.location.hostname}:8080/api/ems/registrations`;
}
