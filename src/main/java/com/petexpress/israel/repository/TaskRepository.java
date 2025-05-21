package com.petexpress.israel.repository;

import com.petexpress.israel.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByTask(String task);
}
