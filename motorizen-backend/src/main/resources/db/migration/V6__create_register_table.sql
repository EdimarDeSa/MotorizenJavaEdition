CREATE TABLE
  IF NOT EXISTS "register" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    "user_id" UUID NOT NULL REFERENCES "users" ("id"),
    "vehicle_id" UUID NOT NULL REFERENCES "vehicle" ("id"),
    "work_time" TIME NOT NULL,
    "register_date" DATE NOT NULL DEFAULT CURRENT_DATE,
    "distance" DECIMAL(10, 4) NOT NULL DEFAULT 0.0,
    "mean_consuption" DECIMAL(10, 4) NOT NULL DEFAULT 0.0,
    "number_of_trips" INTEGER NOT NULL DEFAULT 1,
    "value" DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    "updated_at" TIMESTAMPTZ DEFAULT now (),
    "created_at" TIMESTAMPTZ DEFAULT now (),
    "deleted_at" TIMESTAMPTZ
  );

CREATE INDEX idx_register_user ON "register" ("user_id");

CREATE INDEX idx_register_vehicle ON "register" ("vehicle_id");

CREATE INDEX idx_register_work_time ON "register" ("work_time");

CREATE INDEX idx_register_register_date ON "register" ("register_date");