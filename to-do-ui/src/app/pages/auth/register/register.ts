import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterRequest } from '../../../models/auth/register.request';
import { passwordsMatchValidator } from '../../../validators/passwords-match.validator.directive';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
  private fb = new FormBuilder()

 

  registerForm = this.fb.group({
    email: ["",[Validators.email,Validators.required]],
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

    console.log(request)
  }
}
