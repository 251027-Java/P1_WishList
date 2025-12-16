import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../interfaces/user';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  constructor(private http: HttpClient, private router: Router) {
    // restore persisted state
    const authFlag = localStorage.getItem('auth');
    this._isAuthenticated = authFlag === 'true';
    this._authHeader = localStorage.getItem('authHeader') || undefined;
    const stored = localStorage.getItem('currentUser');
    if (stored) {
      try {
        this._currentUser = JSON.parse(stored) as User;
      } catch (e) {
        this._currentUser = undefined;
      }
    }
  }

  private _isAuthenticated = false;
  private _authHeader?: string;
  private _currentUser?: User;

  authenticateUser(username: string, password: string): void {
    this._isAuthenticated = true;

    // Store auth flag
    localStorage.setItem('auth', 'true');

    // Store encoded credentials
    const encoded = btoa(`${username}:${password}`);
    this._authHeader = `Basic ${encoded}`;
    localStorage.setItem('authHeader', this._authHeader);
  }

  getAuthHeader(): string | null {
    return this._authHeader || localStorage.getItem('authHeader');
  }

  isAuthenticated(): boolean {
    return this._isAuthenticated || localStorage.getItem('auth') === 'true';
  }

  loadCurrentUser(): Observable<User> {
    const headers = { Authorization: this.getAuthHeader() || '' };
    return this.http.get<User>('http://localhost:8080/api/users/me', {headers});
  }

  setCurrentUser(user: User) {
    this._currentUser = user;
    try {
      localStorage.setItem('currentUser', JSON.stringify(user));
    } catch (e) {
      // ignore
    }
  }

  getCurrentUser(): User | undefined {
    return this._currentUser;
  }

  logout(): void {
    this._isAuthenticated = false;
    this._authHeader = undefined;

    localStorage.removeItem('auth');
    localStorage.removeItem('authHeader');
    localStorage.removeItem('currentUser');

    this.router.navigateByUrl('/');
  }

  init(): Observable<User> | null {
    if (!this.isAuthenticated()) return null;
    return this.loadCurrentUser();
  }
}
