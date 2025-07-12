CREATE TABLE
  IF NOT EXISTS "brand" (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE CHECK (trim("name") <> ''),
    "updated_at" TIMESTAMPTZ DEFAULT now (),
    "created_at" TIMESTAMPTZ DEFAULT now (),
    "deleted_at" TIMESTAMPTZ
  );