CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE post_techs (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    post_id UUID,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    tech_id UUID,
    FOREIGN KEY (tech_id) REFERENCES techs(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);