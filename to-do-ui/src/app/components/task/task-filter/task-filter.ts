import { Component, input, output } from '@angular/core';
import { StatusValue } from '../../../constants/StatusValue';
import { FormsModule } from '@angular/forms';
import { CategoryDTO } from '../../../models/task/category.dto';
import { TagDTO } from '../../../models/task/tag.dto';
import { TaskFilterDTO } from '../../../models/task/task.filter.dto';

@Component({
  selector: 'app-task-filter',
  imports: [FormsModule],
  templateUrl: './task-filter.html',
  styleUrl: './task-filter.css'
})
export class TaskFilter {

  filterApplied = output<TaskFilterDTO> ();


  categories = input.required<CategoryDTO[]>();
  tags = input.required<TagDTO[]>();

  StatusValue = StatusValue;
  categoryIds: number[] = [];
  tagIds: number[] = [];
  priorities: number[] = [];
  status : StatusValue[] = [];

  toggleCategory(categoryId: number|undefined, event: Event) {
    if(!categoryId) return;

    const checked = (event.target as HTMLInputElement).checked;
    if(checked) {
      this.categoryIds.push(categoryId);
    } else {
      this.categoryIds = this.categoryIds.filter(id => id !== categoryId);
    }
  }

  toggleTag(tagId: number|undefined, event: Event) {
    if(!tagId) return;

    const checked = (event.target as HTMLInputElement).checked;
    if(checked) {
      this.tagIds.push(tagId);
    } else {
      this.tagIds = this.tagIds.filter(id => id !== tagId);
    }
  }
  
  togglePriority(priority: number, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if(checked) {
      this.priorities.push(priority);
    } else {
      this.priorities = this.priorities.filter(p => p !== priority);
    }
  }

  toggleStatus(status: StatusValue, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    if(checked) {
      this.status.push(status);
    } else {
      this.status = this.status.filter(s => s !== status);
    }
  }

  clearFilter() {
    this.categoryIds = [];
    this.tagIds = [];
    this.priorities = [];
    this.status = [];
    this.applyFilter();
}
  applyFilter() {
    this.filterApplied.emit({
      categoryIds: this.categoryIds,
      tagIds: this.tagIds,
      priorities: this.priorities,
      status: this.status
    });
  }
}
