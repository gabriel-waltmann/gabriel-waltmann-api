CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE post_files (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    post_id UUID,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    file_id UUID,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);