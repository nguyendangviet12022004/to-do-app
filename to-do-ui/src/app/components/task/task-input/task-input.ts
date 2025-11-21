import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { TaskDTO } from '../../../models/task/task.dto';
import { FormArray, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { beforeTodayValidator } from '../../../validators/before-today.validator.directive';
import { TaskService } from '../../../services/task.service';
import { CategoryDTO } from '../../../models/task/category.dto';
import { TagDTO } from '../../../models/task/tag.dto';
import { forkJoin } from 'rxjs';
import { TaskTitleExistsValidator } from '../../../validators/task-title-exists.validator.directive';

@Component({
  selector: 'app-task-input',
  imports: [ReactiveFormsModule],
  templateUrl: './task-input.html',
  styleUrl: './task-input.css'
})
export class TaskInput {

  private readonly taskService = inject(TaskService)
  private readonly taskTitleExistsValidator = inject(TaskTitleExistsValidator)
  categories: CategoryDTO[] = [];
  private readonly cdr = inject(ChangeDetectorRef);

  tags: TagDTO[] = [];


  task: TaskDTO = {
    title: '',
    description: '',
    dueDate: Date.now().toString(),
    category: undefined,
    tags: [],
    priority: 2,
  };

  constructor() {
    forkJoin({
      categoryList: this.taskService.getAllCategory(),
      tagList: this.taskService.getAllTag()}
    ).subscribe(({categoryList, tagList}) => {
      this.categories = categoryList;
      this.tags = tagList;

      const tagFormArray = this.taskForm.get('tagIds') as FormArray;
      this.tags.forEach(() => tagFormArray.push(new FormControl(false)));
      this.cdr.detectChanges();
    });
  }

  // task input form
  taskForm = new FormGroup({
    title: new FormControl(this.task.title, {
      validators: [Validators.required],
      asyncValidators: [this.taskTitleExistsValidator.validate.bind(this.taskTitleExistsValidator)],
      updateOn: 'blur'
    }),
    description: new FormControl(this.task.description),
    dueDate: new FormControl(this.task.dueDate, [beforeTodayValidator()]),
    categoryId: new FormControl<number | undefined>(undefined),
    tagIds: new FormArray<FormControl<boolean>>([]),
    priority: new FormControl(this.task.priority),
  })

  get dueDate() {
    return this.taskForm.get('dueDate') as FormControl;
  }

  get title() {
    return this.taskForm.get('title') as FormControl;
  }

  get tagIds(){
    return this.taskForm.get("tagIds") as FormArray;
  }

  // on form submit
  onSubmit() {
    if (this.taskForm.valid) {
      const formValue = this.taskForm.value;
      this.task.title = formValue.title || '';
      this.task.description = formValue.description || '';
      this.task.dueDate = formValue.dueDate || '';
      this.task.category = {
        id: formValue.categoryId || undefined
      }

      this.task.tags =  this.tagIds.controls.map((control, i) => control.value ? {id: this.tags[i].id} : null).filter(v => v !== null) as TagDTO[];
      this.task.priority = formValue.priority || 2;

      this.taskService.createTask(this.task).subscribe({
        next: (createdTask) => {
          console.log('Task created successfully:', createdTask);
          this.taskForm.reset();
          
          this.tagIds.controls.forEach(control => control.setValue(false));
        },
        error: (error) => {
          console.error('Error creating task:', error);
        }
      });
    }

    


  }
}

