import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ActivateRequest } from '../../../models/auth/activate.request';
import { AuthService } from '../../../services/auth.service';


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

  constructor(){
    this.route.params.subscribe((params) => {
      this.codeSent.set(params['codeSent']=='true' || true);
    });
  }

  onActivate(){
    this.authService.activateAcocunt(this.codeRequest).subscribe({
      next: (value) => {
        console.log(value)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
