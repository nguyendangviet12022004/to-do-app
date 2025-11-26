import { Component, inject, OnInit, signal, Signal } from '@angular/core';
import { TaskInput } from "../../../components/task/task-input/task-input";
import { TaskList } from "../../../components/task/task-list/task-list";
import { TaskService } from '../../../services/task.service';
import { TaskDTO } from '../../../models/task/task.dto';
import { TagDTO } from '../../../models/task/tag.dto';
import { CategoryDTO } from '../../../models/task/category.dto';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-task',
  imports: [TaskInput, TaskList],
  templateUrl: './task.html',
  styleUrl: './task.css'
})
export class Task implements OnInit {

  private taskService = inject(TaskService);

  tasks = signal<TaskDTO[]>([]);
  tags = signal<TagDTO[]>([]);
  categories = signal<CategoryDTO[]>([]);

  
  ngOnInit(): void {

    forkJoin({
      tasks: this.taskService.getTasks(),
      tags: this.taskService.getAllTag(),
      categories: this.taskService.getAllCategory()
    }).subscribe({
      next: ({tasks, tags, categories}) => {
        this.tasks.set(tasks || []);
        this.tags.set(tags || []);
        this.categories.set(categories || []);
        console.log('Fetched tasks:', this.tasks());
        console.log('Fetched tags:', this.tags());
        console.log('Fetched categories:', this.categories());
      },
      error: (err) => {
        console.error('Error fetching data:', err);
      }
    });
  }
}
