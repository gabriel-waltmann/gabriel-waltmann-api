CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE user_links (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    link_id UUID,
    FOREIGN KEY (link_id) REFERENCES links(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);