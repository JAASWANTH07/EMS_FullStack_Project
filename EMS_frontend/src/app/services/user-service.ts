import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import User from '../models/user.model';
import UserResponse from '../models/user-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: string = `http://${window.location.hostname}:8080/api/ems/users`;

  constructor(private httpClient:HttpClient) {};

  getAllUsers(): Observable<User[]> {
    return this.httpClient.get<User[]>(this.baseUrl);
  }

  getAUser(userId:Number): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/${userId}`);
  }

  updateUser(editUser: User): Observable<User> {
    return this.httpClient.put<User>(`${this.baseUrl}/update`,editUser);
  }

  loginUser(user: User): Observable<UserResponse> {
    return this.httpClient.post<UserResponse>(`${this.baseUrl}/auth/login`, user);
  }

  registerUser(newUser: User): Observable<UserResponse> {
    return this.httpClient.post<UserResponse>(`${this.baseUrl}/auth/register`, newUser);
  }
}
