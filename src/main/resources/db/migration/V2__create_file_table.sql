CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE files (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    `key` VARCHAR(100) NOT NULL,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);