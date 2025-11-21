import { inject, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, ValidationErrors } from "@angular/forms";
import { TaskService } from "../services/task.service";
import { catchError, map, Observable, of } from "rxjs";

@Injectable({providedIn: 'root'})
export class TaskTitleExistsValidator implements AsyncValidator {
  private readonly tasksService = inject(TaskService);
  validate(control: AbstractControl): Observable<ValidationErrors | null> {
    return this.tasksService.checkExistsTaskTitle(control.value).pipe(
        map(isExist => (isExist ? { tasktitleexists: true } : null)),
        catchError(() => of(null))
    );

  }
}