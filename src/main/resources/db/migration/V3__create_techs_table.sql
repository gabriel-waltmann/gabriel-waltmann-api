CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE techs (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    file_id UUID,
    FOREIGN KEY (file_id) REFERENCES files(id) ON DELETE CASCADE,
    link_id UUID,
    FOREIGN KEY (link_id) REFERENCES links(id) ON DELETE CASCADE,
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL
);