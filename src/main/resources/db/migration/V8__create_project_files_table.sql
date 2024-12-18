CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE project_files (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    project_id UUID,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    file_id UUID,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);