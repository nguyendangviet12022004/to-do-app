import { Component, inject, OnInit, signal, Signal } from '@angular/core';
import { TaskInput } from "../../../components/task/task-input/task-input";
import { TaskList } from "../../../components/task/task-list/task-list";
import { TaskService } from '../../../services/task.service';
import { TaskDTO } from '../../../models/task/task.dto';

@Component({
  selector: 'app-task',
  imports: [TaskInput, TaskList],
  templateUrl: './task.html',
  styleUrl: './task.css'
})
export class Task implements OnInit {

  private taskService = inject(TaskService);

  tasks = signal<TaskDTO[]>([]);
  
  ngOnInit(): void {
    this.taskService.getTasks().subscribe({
      next: (data) => {
        this.tasks.set(data || []);
        console.log('Fetched tasks:', this.tasks());
      },
      error: (err) => {
        console.error('Error fetching tasks:', err);
      }
    });

    
  }
}
