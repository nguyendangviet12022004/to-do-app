import { Component } from '@angular/core';
import { LoginRequest } from '../../../models/auth/login.request';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  loginRequest: LoginRequest = {
    email: "",
    password: ""
  }
}
