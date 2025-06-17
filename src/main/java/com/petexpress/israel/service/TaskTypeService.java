package com.petexpress.israel.service;

import com.petexpress.israel.dto.request.TaskTypeRequestDto;
import com.petexpress.israel.dto.res.TaskTypeResponseDto;
import com.petexpress.israel.entities.TaskType;
import com.petexpress.israel.repository.TaskTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskTypeService {

    @Autowired
    private TaskTypeRepository repository;

    public TaskTypeResponseDto create(TaskTypeRequestDto dto) {
        if (repository.findByName(dto.name()).isPresent()) {
            throw new RuntimeException("TaskType with this name already exists.");
        }

        TaskType type = new TaskType();
        type.setName(dto.name());
        type.setDescription(dto.description());

        TaskType saved = repository.save(type);

        return new TaskTypeResponseDto(saved.getId(), saved.getName(), saved.getDescription());
    }

    public List<TaskTypeResponseDto> getAll() {
        return repository.findAll().stream()
                .map(item -> new TaskTypeResponseDto(item.getId(), item.getName(), item.getDescription()))
                .toList();
    }

    public TaskTypeResponseDto getById(UUID id) {
        TaskType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskType not found."));

        return new TaskTypeResponseDto(type.getId(), type.getName(), type.getDescription());
    }

    public TaskTypeResponseDto update(UUID id, TaskTypeRequestDto dto) {
        TaskType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskType not found."));

        type.setName(dto.name());
        type.setDescription(dto.description());

        TaskType updated = repository.save(type);

        return new TaskTypeResponseDto(updated.getId(), updated.getName(), updated.getDescription());
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TaskType not found.");
        }
        repository.deleteById(id);
    }

}
