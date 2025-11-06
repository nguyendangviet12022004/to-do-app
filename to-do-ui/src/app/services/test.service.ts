import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class TestService {
  private readonly httpClient = inject(HttpClient)

  request(){
    return this.httpClient.get(`${environment.apiUrl}/test`);
  }
}
