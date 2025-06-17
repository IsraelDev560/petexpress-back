CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE animals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    specie VARCHAR(255) NOT NULL
);

CREATE TABLE task_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role user_role NOT NULL
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_type_id UUID NOT NULL,
    description TEXT NOT NULL,
    animal_id UUID NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_task_type FOREIGN KEY (task_type_id) REFERENCES task_types(id),
    CONSTRAINT fk_animal FOREIGN KEY (animal_id) REFERENCES animals(id)
);