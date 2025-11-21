import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { CategoryDTO } from '../models/task/category.dto';
import { TagDTO } from '../models/task/tag.dto';
import { TaskDTO } from '../models/task/task.dto';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private readonly httpClient = inject(HttpClient)

  getAllTasks() {
    return this.httpClient.get(`${environment.apiUrl}/task`);
  }
  
  getAllCategory(){
    return this.httpClient.get<CategoryDTO[]>(`${environment.apiUrl}/category`);
  }

  getAllTag(){
    return this.httpClient.get<TagDTO[]>(`${environment.apiUrl}/tag`);
  }

  checkExistsTaskTitle(title: string) {
    return this.httpClient.get<boolean>(`${environment.apiUrl}/task/check-exists-title?title=${title}`);
  }

  createTask(task: TaskDTO) {
    return this.httpClient.post<TaskDTO>(`${environment.apiUrl}/task`, task);
  }
}
