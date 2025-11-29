import { Component, inject, input, OnInit } from '@angular/core';
import { TaskDTO } from '../../../models/task/task.dto';
import { TaskItem } from "../task-item/task-item";

@Component({
  selector: 'app-task-list',
  imports: [ TaskItem],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css'
})
export class TaskList  {
  tasks = input.required<TaskDTO[]>();

}