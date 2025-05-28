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
@Tag(name = "Tasks-Types")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TaskTypeController {

    @Autowired
    private TaskTypeService service;


    @PostMapping
    public ResponseEntity<TaskTypeResponseDto> create(@RequestBody @Valid TaskTypeRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TaskTypeResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskTypeResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskTypeResponseDto> update(@PathVariable UUID id, @RequestBody @Valid TaskTypeRequestDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

