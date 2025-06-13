CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    task_type_id UUID NOT NULL,
    description TEXT NOT NULL,
    animal_id UUID NOT NULL,
    date DATE NOT NULL,

    CONSTRAINT fk_task_type FOREIGN KEY (task_type_id) REFERENCES task_types(id),
    CONSTRAINT fk_animal FOREIGN KEY (animal_id) REFERENCES animals(id)
);