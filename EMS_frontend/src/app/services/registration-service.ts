import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  baseUrl: string = 'http://localhost:8080/api/ems/registrations';
}
