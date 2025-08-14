CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE user_techs (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    tech_id UUID,
    FOREIGN KEY (tech_id) REFERENCES techs(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);