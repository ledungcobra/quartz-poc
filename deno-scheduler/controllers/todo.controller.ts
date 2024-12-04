import "https://deno.land/x/reflect_metadata@v0.1.12/mod.ts";
import { Body, Controller, Delete, Get, Param, Post } from "../deps.ts";
import { TodoService } from "../services/todo-service.ts";

@Controller("/todos")
export class TodoController {
  constructor(private readonly todoService: TodoService) {}

  @Get()
  getAll() {
    return this.todoService.findAll();
  }

  @Post()
  create(@Body() todo: Todo) {
    return this.todoService.create(todo);
  }

  @Delete("/:id")
  delete(@Param("id") id: string) {
    const todo = this.todoService.delete(id);
    if (!todo) {
      throw new Error("Todo not found");
    }
    return todo;
  }
}
