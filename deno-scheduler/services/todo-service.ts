import { Injectable } from "../deps.ts";

@Injectable()
export class TodoService {
  private todos: Todo[] = [];

  findAll(): Todo[] {
    return this.todos;
  }

  create(todo: Todo): Todo {
    this.todos.push(todo);
    return todo;
  }

  delete(id: string): Todo | undefined {
    const index = this.todos.findIndex(todo => todo.id === id);
    if (index !== -1) {
      const todo = this.todos[index];
      this.todos.splice(index, 1);
      return todo;
    }
    return undefined;
  }
} 