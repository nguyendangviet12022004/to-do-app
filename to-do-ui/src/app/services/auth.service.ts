import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../environments/enviroment.dev';
import { RegisterRequest } from '../models/auth/register.request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly httpClient = inject(HttpClient)

  register(request: RegisterRequest): Observable<any>{
    return this.httpClient.post(`${environment.apiUrl}/auth/register`,request)
  }

  checkEmailExists(email: string) :Observable<boolean>{
    return this.httpClient.get<boolean>(`${environment.apiUrl}/auth/check-email?email=${email}`)
  }
}
