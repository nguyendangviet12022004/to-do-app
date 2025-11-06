import {Component, inject, signal} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {ActivateRequest} from '../../../models/auth/activate.request';
import {AuthService} from '../../../services/auth.service';
import {errorCode} from '../../../constants/ErrorCode';


@Component({
  selector: 'app-activate',
  imports: [FormsModule],
  templateUrl: './activate.html',
  styleUrl: './activate.css'
})
export class Activate {
  codeSent = signal(false);
  private route = inject(ActivatedRoute)
  private authService = inject(AuthService)
  codeRequest: ActivateRequest = {
    code: ''
  }
  email = signal('');

  errorMessage= signal('');

  constructor(){
    this.route.queryParams.subscribe((params) => {
      console.log(params)
      this.codeSent.set(params['codeSent']=='true' || false);
      console.log(this.codeSent())
    });
  }

  onActivate(){
    this.authService.activateAcocunt(this.codeRequest).subscribe({
      next: (value) => {
        // to do redirect to login page

      },
      error: (err) => {
        console.log(err.error.code)
        switch(err.error.code){
          case errorCode.TOKEN_NOT_EXISTS:

            this.errorMessage.set("The activation code is invalid.")
            break;
          case errorCode.TOKEN_EXPIRED:
            this.errorMessage.set("The activation code has expired");
            this.codeSent.set(false)
            break;
        }
      }
    })
  }

  onGetActivateCode(){
    this.authService.getActivateCode(this.email()).subscribe({
      next: (value) => {
        this.codeSent.set(true)
        this.errorMessage.set("")
      },
      error: (err) => {
        console.log(err)
        this.errorMessage.set(err.error.message);
      }
    })
  }
}
