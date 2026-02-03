CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

INSERT INTO users (name, email) VALUES ('admin', 'admin@example.com');
INSERT INTO users (name, email) VALUES ('user', 'user@example.com');
