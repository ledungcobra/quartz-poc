import { TodoController } from "../controllers/todo.controller.ts";
import { Module } from "../deps.ts";
import { TodoService } from "../services/todo-service.ts";

@Module({
  controllers: [TodoController],
  routePrefix: "/todos",
  providers: [TodoService],
})
export class AppModule {}
