import { Component, inject, OnInit, signal } from '@angular/core';
import { forkJoin } from 'rxjs';
import { TaskFilter } from "../../../components/task/task-filter/task-filter";
import { TaskInput } from "../../../components/task/task-input/task-input";
import { TaskList } from "../../../components/task/task-list/task-list";
import { CategoryDTO } from '../../../models/task/category.dto';
import { TagDTO } from '../../../models/task/tag.dto';
import { TaskDTO } from '../../../models/task/task.dto';
import { TaskService } from '../../../services/task.service';
import { TaskFilterDTO } from '../../../models/task/task.filter.dto';

@Component({
  selector: 'app-task',
  imports: [TaskInput, TaskList, TaskFilter],
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
      },
      error: (err) => {
        console.error('Error fetching data:', err);
      }
    });
  }

  onFilter($event: TaskFilterDTO) {
  this.taskService.getTasks($event.status, $event.priorities, $event.categoryIds, $event.tagIds).subscribe({

    next: (tasks) => {
      console.log('Filtered tasks:', tasks);
      this.tasks.set(tasks || []);}});
  }
}
