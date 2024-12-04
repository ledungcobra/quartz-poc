import Bree from "npm:bree@9.2.4";
import { db } from "../config/database.ts";

export class BreeScheduler {
  private bree: Bree;

  constructor() {
    this.bree = new Bree({
      root: false,
      jobs: [],

    });
    this.loadPersistedJobs();
  }

  private async loadPersistedJobs() {
    const jobs = await db.queryEntries<{
      name: string;
      cron: string;
      path: string;
      data: string;
    }>("SELECT * FROM jobs");

    for (const job of jobs) {
      this.bree.add({
        name: job.name,
        cron: job.cron,
        path: job.path,
        data: JSON.parse(job.data),
      });
    }
  }

  async start() {
    await this.bree.start();
  }

  async stop() {
    await this.bree.stop();
  }

  async addJob(jobConfig: Bree.JobOptions) {
    await this.bree.add(jobConfig);
  }

  async removeJob(jobName: string) {
    await this.bree.remove(jobName);
  }
}
