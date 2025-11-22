import { Component, input } from '@angular/core';
import { TaskDTO } from '../../../models/task/task.dto';
import { StatusValue } from '../../../constants/StatusValue';

@Component({
  selector: 'app-task-item',
  imports: [],
  templateUrl: './task-item.html',
  styleUrl: './task-item.css'
})
export class TaskItem {
  task = input.required<TaskDTO>();

  StatusValue = StatusValue
}
