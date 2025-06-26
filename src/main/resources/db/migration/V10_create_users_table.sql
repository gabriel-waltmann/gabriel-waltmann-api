CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users   (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(500) NOT NULL,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);