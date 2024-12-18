CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE posts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL,
    file_id UUID,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);