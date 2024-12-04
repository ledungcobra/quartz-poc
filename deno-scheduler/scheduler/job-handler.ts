interface Job {
  name: string;
  endpoint: string;
  interval: number;
  type: 'REPEATED' | 'ONESHOT';
}

interface JobHandler {
  createJob(name: string, endpoint: string): Promise<void>;
  deleteJob(name: string): Promise<void>;
}
