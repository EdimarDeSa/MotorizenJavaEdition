CREATE TABLE
  IF NOT EXISTS "backlog_event" (
    "id" SERIAL PRIMARY KEY,
    "backlog_event_type_id" INTEGER NOT NULL REFERENCES "backlog_event_type" ("id"),
    "user_id" UUID NOT NULL REFERENCES "users" ("id"),
    "comment" TEXT CHECK (
      "comment" IS NULL
      OR trim("comment") <> ''
    ),
    "created_at" TIMESTAMPTZ DEFAULT now ()
  );

CREATE INDEX idx_backlog_event_type ON "backlog_event" ("backlog_event_type_id");

CREATE INDEX idx_backlog_user ON "backlog_event" ("user_id");

CREATE INDEX idx_backlog_created_at ON "backlog_event" ("created_at");