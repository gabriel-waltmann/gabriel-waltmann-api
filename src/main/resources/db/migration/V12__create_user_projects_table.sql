CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE user_projects (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    project_id UUID,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);