CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE project_techs (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    project_id UUID,
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    tech_id UUID,
    FOREIGN KEY (tech_id) REFERENCES techs(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);