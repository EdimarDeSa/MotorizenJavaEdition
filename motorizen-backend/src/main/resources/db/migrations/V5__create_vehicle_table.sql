CREATE TABLE
  IF NOT EXISTS "vehicle" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    "user_id" UUID NOT NULL REFERENCES "users" ("id") ON DELETE CASCADE,
    "brand_id" INTEGER NOT NULL REFERENCES "brand" ("id"),
    "fuel_type_id" INTEGER NOT NULL REFERENCES "fuel_type" ("id"),
    "model" VARCHAR(100) NOT NULL,
    "renavam" VARCHAR(11) UNIQUE DEFAULT NULL,
    "year" SMALLINT NOT NULL,
    "color" VARCHAR(25) NOT NULL,
    "license_plate" VARCHAR(10) UNIQUE DEFAULT NULL,
    "fuel_capacity" DECIMAL(10, 4) NOT NULL,
    "odometer" DECIMAL(10, 4) NOT NULL,
    "is_active" BOOLEAN DEFAULT TRUE NOT NULL,
    "updated_at" TIMESTAMPTZ NOT NULL DEFAULT now (),
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT now (),
    "deleted_at" TIMESTAMPTZ
  );

CREATE INDEX idx_vehicle_user ON "vehicle" ("user_id");

CREATE INDEX idx_vehicle_brand ON "vehicle" ("brand_id");