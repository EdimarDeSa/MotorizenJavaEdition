CREATE TABLE IF NOT EXISTS "backlog_severity" (
  "id" SERIAL PRIMARY KEY,
  "name" CITEXT NOT NULL UNIQUE CHECK (char_length("name") <= 20),
);