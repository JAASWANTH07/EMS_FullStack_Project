import { Injectable } from '@angular/core';
import User from '../models/user.model';
import Role from '../models/role.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn: boolean = false;

  constructor() { }

  storeToken(token: string) {
    localStorage.setItem('jwtToken', token);
  }

  deleteToken(): void {
    localStorage.removeItem('jwtToken');
  }

  retrieveToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  storeUserInfo(user: User): void {
    localStorage.setItem('userInfo', JSON.stringify(user));
  }

  deleteUserInfo(): void{
    localStorage.removeItem('userInfo');
  }

  retrieveUserInfo(): User | null {
    let fetchedUserInfoString = localStorage.getItem('userInfo');
    if (fetchedUserInfoString !== null)
    {
      return JSON.parse(fetchedUserInfoString);
    }

    return null;
  }

  retrieveRole(): Role[] | undefined {
    let user = this.retrieveUserInfo();
    return user?.allRoles;
  }

  isOrganizer(): boolean {
    let user = this.retrieveUserInfo();
    if (user?.allRoles.find((role) => role.roleId == 1))
    {
      return true;
    }
    return false;
  }

  isParticipant(): boolean {
    let user = this.retrieveUserInfo();
    if (user?.allRoles.find((role) => role.roleId == 2))
    {
      return true;
    }
    return false;
  }
}

