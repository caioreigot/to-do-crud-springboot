import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EditService } from 'src/app/services/edit.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.less']
})
export class TaskComponent {

  @Input() id: number = 0;
  @Input() title: string = "";
  @Input() description: string = "";
  @Output() taskIdToDelete = new EventEmitter<number>();
  
  constructor(private editService: EditService) {}

  onClick() {
    this.editService.current = {
      id: this.id,
      title: this.title,
      description: this.description
    };
  }

  removeTask(event: Event, taskId: number) {
    this.taskIdToDelete.next(taskId);
    event.stopPropagation();
  }
}
