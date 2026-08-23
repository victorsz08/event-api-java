CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE event (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255),
    description TEXT,
    image_url VARCHAR(512),
    event_url VARCHAR(512),
    remote BOOLEAN,
    date TIMESTAMP
);