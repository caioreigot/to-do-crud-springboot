import { Subscription } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { EditService } from './services/edit.service';
import { Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
import { Task } from './model/Task';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnDestroy {

  subscription: Subscription;
  isEditing: boolean = false;

  @ViewChild('list') list: ElementRef<HTMLDivElement> | null = null;
  @ViewChild('edit') edit: ElementRef<HTMLDivElement> | null = null;

  tasks: Task[] | null = null;

  constructor(
    public editService: EditService,
    private http: HttpClient
  ) {
    this.subscription = this.editService.subscribe((task) => {
      this.isEditing = Boolean(task);

      if (!task) {
        this.list?.nativeElement.classList.remove('hidden');
        this.edit?.nativeElement.classList.add('transparent');
        return;
      }

      setTimeout(() => {
        this.list?.nativeElement.classList.add('hidden');
        this.edit?.nativeElement.classList.remove('transparent');
      }, 200);
    });

    this.fetchTasks();
  }

  fetchTasks() {
    this.http.get<Task[]>('http://localhost:8080/tasks').subscribe({
      next: (value) => {
        this.tasks = value;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  add(title: string, description: string) {
    if (!title.trim()) {
      alert('Por favor, escreva um título para a tarefa');
      return;
    }

    this.http.post('http://localhost:8080/tasks', { title, description }).subscribe(
      { complete: () => this.fetchTasks() }
    );
  }

  put(id: number, title: string, description: string) {
    if (!title.trim()) {
      alert('Por favor, escreva um título para a tarefa');
      return;
    }
    
    this.http.put(`http://localhost:8080/tasks/${id}`, { id, title, description }).subscribe(
      { 
        complete: () => { 
          this.fetchTasks();
          alert('Alterações feitas com sucesso');
        } 
      }
    );
  }

  delete(taskId: number) {
    this.http.delete(`http://localhost:8080/tasks/${taskId}`).subscribe(
      { complete: () => this.fetchTasks() }
    );
  }

  closeEdit() {
    this.editService.current = null;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
