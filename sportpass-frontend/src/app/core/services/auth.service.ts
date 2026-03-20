import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface UserInfo {
  email: string;
  nombre: string;
  rol: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUser = new BehaviorSubject<UserInfo | null>(this.loadUser());

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<UserInfo> {
    return this.http.post<UserInfo>(`${this.apiUrl}/login`, { email, password }, { withCredentials: true })
      .pipe(tap(user => this.saveUser(user)));
  }

  register(email: string, password: string, nombre: string): Observable<UserInfo> {
    return this.http.post<UserInfo>(`${this.apiUrl}/register`, { email, password, nombre }, { withCredentials: true })
      .pipe(tap(user => this.saveUser(user)));
  }

  logout(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/logout`, {}, { withCredentials: true })
      .pipe(tap(() => this.clearUser()));
  }

  private saveUser(user: UserInfo): void {
    sessionStorage.setItem('user', JSON.stringify(user));
    this.currentUser.next(user);
  }

  private clearUser(): void {
    sessionStorage.removeItem('user');
    this.currentUser.next(null);
  }

  private loadUser(): UserInfo | null {
    const user = sessionStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  getUser(): UserInfo | null {
    return this.currentUser.getValue();
  }

  getNombre(): string {
    return this.currentUser.getValue()?.nombre || '';
  }

  getRol(): string {
    return this.currentUser.getValue()?.rol || '';
  }

  isAuthenticated(): boolean {
    return this.currentUser.getValue() !== null;
  }

  isLoggedIn(): Observable<UserInfo | null> {
    return this.currentUser.asObservable();
  }
}