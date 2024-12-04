import { DB } from "https://deno.land/x/sqlite/mod.ts";

export const db = new DB("jobs.db");

// Initialize jobs table
db.execute(`
  CREATE TABLE IF NOT EXISTS jobs (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    cron TEXT,
    path TEXT NOT NULL,
    status TEXT DEFAULT 'pending',
    data TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  )
`);
