package com.petexpress.israel.repository;

import com.petexpress.israel.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByAnimalId(UUID animalId);
    List<Task> findByTaskTypeId(UUID taskTypeId);
}
