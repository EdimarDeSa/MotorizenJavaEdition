CREATE TABLE
  IF NOT EXISTS "backlog_event_type" (
    "id" SERIAL PRIMARY KEY,
    "name" CITEXT NOT NULL UNIQUE CHECK (char_length("name") <= 50),
    "backlog_severity_id" INTEGER NOT NULL REFERENCES "backlog_severity" ("id")
  );

CREATE INDEX idx_backlog_event_type_severity ON "backlog_event_type" ("backlog_severity_id");