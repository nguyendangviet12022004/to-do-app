import { Component, inject, signal } from '@angular/core';
import { LoginRequest } from '../../../models/auth/login.request';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { catchError, of, switchMap, throwError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  private authService = inject(AuthService);
  private router = inject(Router);

  loginRequest: LoginRequest = {
    email: "",
    password: ""
  }

  errorMessage = signal<string>("");

  onLogin(){
    this.authService.login(this.loginRequest).pipe(
      catchError((err) => {
        if(err.error?.code === "DISABLED_ACCOUNT"){
          return this.authService.getActivateCode(this.loginRequest.email).pipe(
            switchMap(() => {
              this.router.navigate(['/auth/activate'], {queryParams: {codeSent: true}});
              return of(null);
            })
          )
        }

        return throwError(() => err)
      })
    ).subscribe({
      next: (response) => {
       this.authService.saveTokens(response!.accessToken, response!.refreshToken);
       this.router.navigate(['/']);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message || "An error occurred during login. Please try again.");
      }
    })
  }
}
