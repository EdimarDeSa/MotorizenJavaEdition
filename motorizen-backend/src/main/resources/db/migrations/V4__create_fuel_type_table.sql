CREATE TABLE
  IF NOT EXISTS "fuel_type" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(20) NOT NULL UNIQUE CHECK (trim("name") <> ''),
    "updated_at" TIMESTAMPTZ DEFAULT now (),
    "created_at" TIMESTAMPTZ DEFAULT now (),
    "deleted_at" TIMESTAMPTZ
  );