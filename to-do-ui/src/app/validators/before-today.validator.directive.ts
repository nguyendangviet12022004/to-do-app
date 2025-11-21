import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function beforeTodayValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const inputDate = new Date(control.value);
    const today = new Date();
    today.setHours(0, 0, 0, 0); 

    return inputDate < today ? { beforeToday: true } : null;
  };
}