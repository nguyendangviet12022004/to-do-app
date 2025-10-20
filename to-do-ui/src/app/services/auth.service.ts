import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../environments/enviroment.dev';
import { RegisterRequest } from '../models/auth/register.request';
import { Observable } from 'rxjs';
import { ActivateRequest } from '../models/auth/activate.request';
import { LoginRequest } from '../models/auth/login.request';
import { LoginResponse } from '../models/auth/login.response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly httpClient = inject(HttpClient)

  private refreshTokenSignal = signal<string>("");
  private accessTokenSignal = signal<string>("");

  constructor() {
  const accessToken = localStorage.getItem('access_token') || '';
  const refreshToken = localStorage.getItem('refresh_token') || '';
  this.accessTokenSignal.set(accessToken);
  this.refreshTokenSignal.set(refreshToken);
}

  register(request: RegisterRequest): Observable<any>{
    return this.httpClient.post(`${environment.apiUrl}/auth/register`,request)
  }

  checkEmailExists(email: string) :Observable<boolean>{
    return this.httpClient.get<boolean>(`${environment.apiUrl}/auth/check-email?email=${email}`)
  }

  activateAcocunt(ActivateRequest: ActivateRequest): Observable<any>{
    return this.httpClient.post(`${environment.apiUrl}/auth/activate-account`, ActivateRequest)
  }

  getActivateCode(email: string): Observable<any>{
    let params:HttpParams = new HttpParams().set('email', email);
    return this.httpClient.get(`${environment.apiUrl}/auth/activate-account-code`, {params: params})
  }

  login(request: LoginRequest): Observable<LoginResponse>{
    return this.httpClient.post<LoginResponse>(`${environment.apiUrl}/auth/login`, request)
  }

  saveTokens(accessToken: string, refreshToken: string): void {
    this.accessTokenSignal.set(accessToken);
    this.refreshTokenSignal.set(refreshToken);
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('refresh_token', refreshToken);
  }

  get accessToken(){
    return this.accessTokenSignal();
  }

  get refreshToken(){
    return this.refreshTokenSignal();
  }

  isAuthenticated(){
    return !!this.accessTokenSignal();
  }
  
}
