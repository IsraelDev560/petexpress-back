package com.petexpress.israel.service;

import com.petexpress.israel.dto.request.TaskRequestDto;
import com.petexpress.israel.dto.res.TaskResponseDto;
import com.petexpress.israel.dto.update.TaskUpdateDto;
import com.petexpress.israel.entities.Animal;
import com.petexpress.israel.entities.Task;
import com.petexpress.israel.entities.TaskType;
import com.petexpress.israel.repository.AnimalRepository;
import com.petexpress.israel.repository.TaskRepository;
import com.petexpress.israel.repository.TaskTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTypeRepository taskTypeRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll().stream().map(task -> new TaskResponseDto(
                task.getId(),
                task.getTaskType().getName(),
                task.getTaskType().getDescription(),
                task.getDate(),
                task.getAnimal().getId(),
                task.getAnimal().getName(),
                task.getAnimal().getSpecie()
        )).toList();
    }

    public TaskResponseDto createTask(TaskRequestDto dto) {
        TaskType type = taskTypeRepository.findByName(dto.taskTypeName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Type not found"));
        Animal animal = animalRepository.findById(dto.animalId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        Task task = new Task();
        task.setTaskType(type);
        task.setAnimal(animal);
        task.setDescription(dto.description());
        task.setDate(dto.date());

        Task saved = taskRepository.save(task);

        return new TaskResponseDto(
                saved.getId(),
                type.getName(),
                type.getDescription(),
                saved.getDate(),
                animal.getId(),
                animal.getName(),
                animal.getSpecie()
        );
    }

    public TaskResponseDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        return new TaskResponseDto(
                task.getId(),
                task.getTaskType().getName(),
                task.getTaskType().getDescription(),
                task.getDate(),
                task.getAnimal().getId(),
                task.getAnimal().getName(),
                task.getAnimal().getSpecie()
        );
    }

    @Transactional
    public TaskResponseDto updateTask(UUID id, TaskUpdateDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

        TaskType type = taskTypeRepository.findById(dto.taskTypeId())
                .orElseThrow(() -> new RuntimeException("TaskType not found"));

        Animal animal = animalRepository.findById(dto.animalId())
                .orElseThrow(() -> new RuntimeException("Animal not found"));

        task.setTaskType(type);
        task.setAnimal(animal);
        task.setDate(dto.date());

        Task updated = taskRepository.save(task);

        return new TaskResponseDto(
                updated.getId(),
                type.getName(),
                type.getDescription(),
                updated.getDate(),
                animal.getId(),
                animal.getName(),
                animal.getSpecie()
        );
    }

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}
