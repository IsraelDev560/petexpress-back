package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.TaskTypeRequestDto;
import com.petexpress.israel.dto.res.TaskTypeResponseDto;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.TaskTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("task-types")
@Tag(name = "Task Types", description = "Manage the types of tasks that can be assigned to animals.")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TaskTypeController {

    @Autowired
    private TaskTypeService service;

    @Operation(summary = "Create a new task type", description = "Registers a new task type in the system.")
    @PostMapping
    public ResponseEntity<TaskTypeResponseDto> create(@RequestBody @Valid TaskTypeRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "List all task types", description = "Returns all registered task types.")
    @GetMapping
    public ResponseEntity<List<TaskTypeResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get task type by ID", description = "Fetches a specific task type by its unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<TaskTypeResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update task type by ID", description = "Updates a task type's data using its ID.")
    @PutMapping("/{id}")
    public ResponseEntity<TaskTypeResponseDto> update(@PathVariable UUID id, @RequestBody @Valid TaskTypeRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete task type by ID", description = "Removes a task type from the system using its ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

