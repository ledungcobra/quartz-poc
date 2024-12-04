import { Application } from "https://deno.land/x/oak@v17.1.3/mod.ts";
import { assignModule } from "https://deno.land/x/oak_decorators@v0.2.1/mod.ts";
import { AppModule } from "./modules/index.ts";
import { BreeScheduler } from "./scheduler/bree-scheduler.ts";

const app = new Application();
const scheduler = new BreeScheduler();

//@ts-ignore the api return type is not defined
app.use(assignModule(AppModule));

// Start both the web server and scheduler
Promise.all([
  app.listen({ port: 8080 }),
  scheduler.start()
]).then(() => {
  console.log("Server and scheduler are running");
});
