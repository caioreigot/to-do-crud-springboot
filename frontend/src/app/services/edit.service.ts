import { Injectable } from '@angular/core';
import { Task } from '../model/Task';
import { BehaviorSubject, Subscription } from 'rxjs';

type TaskOrNull = Task | null;

@Injectable({
  providedIn: 'root'
})
export class EditService {

  private _current: BehaviorSubject<TaskOrNull> = new BehaviorSubject<TaskOrNull>(null);

  subscribe(callback: (value: TaskOrNull) => void): Subscription {
    return this._current.subscribe(value => callback(value));
  }

  get current(): TaskOrNull {
    return this._current.value;
  }

  set current(value: TaskOrNull) {
    this._current.next(value);
  }

  constructor() {}
}
