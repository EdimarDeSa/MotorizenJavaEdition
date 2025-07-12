CREATE TABLE
  IF NOT EXISTS "users" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    "first_name" VARCHAR(50) NOT NULL CHECK (trim("first_name") <> ''),
    "last_name" VARCHAR(100) NOT NULL CHECK (trim("last_name") <> ''),
    "email" CITEXT NOT NULL UNIQUE CHECK (
      "email" ~* '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' 
      AND char_length("email") <= 255
    ),
    "password" VARCHAR(100) NOT NULL CHECK (trim("password") <> ''),
    "birthdate" DATE NOT NULL CHECK (
      "birthdate" BETWEEN '1900-01-01' AND CURRENT_DATE  - INTERVAL '18 years'
    ),
    "is_active" BOOLEAN DEFAULT TRUE NOT NULL,
    "is_administrator" BOOLEAN DEFAULT FALSE NOT NULL,
    "updated_at" TIMESTAMPTZ DEFAULT now (),
    "created_at" TIMESTAMPTZ DEFAULT now (),
    "deleted_at" TIMESTAMPTZ
  );