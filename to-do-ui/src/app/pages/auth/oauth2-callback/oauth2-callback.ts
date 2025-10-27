import { Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-oauth2-callback',
  imports: [],
  templateUrl: './oauth2-callback.html',
  styleUrl: './oauth2-callback.css'
})
export class Oauth2Callback {

  private route = inject(ActivatedRoute)
  private router = inject(Router)
  private authService = inject(AuthService)
  constructor(){
    this.route.queryParams.subscribe((params) => {
      const accessToken = params["accessToken"]
      const refreshToken = params["refreshToken"]
      this.authService.saveTokens(accessToken,refreshToken)

      this.router.navigate(["/"]);
    });
  }
}
