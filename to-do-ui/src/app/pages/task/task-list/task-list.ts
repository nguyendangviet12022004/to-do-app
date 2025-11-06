import {Component, inject, OnInit} from '@angular/core';
import {TestService} from '../../../services/test.service';

@Component({
  selector: 'app-task-list',
  imports: [],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css'
})
export class TaskList implements OnInit{
  private testService = inject(TestService)
  ngOnInit() {
    this.testService.request().subscribe({
      next: data => console.log(data),
      error: err => console.log(err)
    });
  }
}
