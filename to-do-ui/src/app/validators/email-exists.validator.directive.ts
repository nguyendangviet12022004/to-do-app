import { inject, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, ValidationErrors } from "@angular/forms";
import { catchError, map, Observable, of } from "rxjs";
import { AuthService } from "../services/auth.service";

@Injectable({providedIn: 'root'})
export class EmailExistsValidator implements AsyncValidator{
    
    private readonly authService = inject(AuthService)

    validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
        return this.authService.checkEmailExists(control.value).pipe(
            map(isExist => (isExist ? {emailexists: true} : null)),
            catchError(() => of(null)),
        )    
    }
}