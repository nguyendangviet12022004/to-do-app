import { Component } from '@angular/core';
import { TaskInput } from "../../../components/task/task-input/task-input";

@Component({
  selector: 'app-task',
  imports: [TaskInput],
  templateUrl: './task.html',
  styleUrl: './task.css'
})
export class Task {

}
