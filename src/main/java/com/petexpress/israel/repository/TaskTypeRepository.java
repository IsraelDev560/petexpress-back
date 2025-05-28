package com.petexpress.israel.repository;

import com.petexpress.israel.entities.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskTypeRepository extends JpaRepository<TaskType, UUID> {
    Optional<TaskType> findByName(String name);
}
