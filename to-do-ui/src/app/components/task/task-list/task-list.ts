import { Component, inject, input, OnInit } from '@angular/core';
import { TaskFilter } from "../task-filter/task-filter";
import { TaskDTO } from '../../../models/task/task.dto';
import { TaskItem } from "../task-item/task-item";
import { TaskService } from '../../../services/task.service';

@Component({
  selector: 'app-task-list',
  imports: [TaskFilter, TaskItem],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css'
})
export class TaskList implements OnInit {
  tasks = input.required<TaskDTO[]>();

  ngOnInit(): void {
    console.log(this.tasks())
  }
}