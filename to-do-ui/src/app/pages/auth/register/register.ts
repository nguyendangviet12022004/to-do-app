import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterRequest } from '../../../models/auth/register.request';
import { passwordsMatchValidator } from '../../../validators/passwords-match.validator.directive';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { EmailExistsValidator } from '../../../validators/email-exists.validator.directive';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  private fb = new FormBuilder()
  private authService = inject(AuthService)
  private router = inject(Router)
  private emailExistsValidator = inject(EmailExistsValidator)


  errorMessage = ""

 

  registerForm = this.fb.group({
    email: ["", {
      validators: [Validators.required, Validators.email],
      asyncValidators: [this.emailExistsValidator.validate.bind(this.emailExistsValidator)],
      updateOn: 'blur'  
    }],
    password: this.fb.group({
      newPassword: ["",[Validators.required, Validators.minLength(8)]],
      confirmPassword: ["", [Validators.required]]
    },{
      validators: [passwordsMatchValidator]
    })
  })

  get email(){
    return this.registerForm.controls.email;
  }

  get password(){
    return this.registerForm.controls.password;
  }

  get newPassword(){
    return this.registerForm.controls.password.controls.newPassword;
  }

  get confirmPassword(){
    return this.registerForm.controls.password.controls.confirmPassword;
  }

  onRegister(){
    const request : RegisterRequest = {
      email : this.email.value ?? "",
      password: this.newPassword.value ?? ""
    }

    this.authService.register(request).subscribe(
      {
        next:(value) => {
          this.router.navigate(['/auth/activate', {codeSent: true}])
        },
        error: (err) => {
            console.log(err)
        },
      }
    )
    
  }
}
