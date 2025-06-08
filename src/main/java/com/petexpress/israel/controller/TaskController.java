package com.petexpress.israel.controller;

import com.petexpress.israel.dto.request.TaskRequestDto;
import com.petexpress.israel.dto.res.TaskResponseDto;
import com.petexpress.israel.dto.update.TaskUpdateDto;
import com.petexpress.israel.security.SecurityConfig;
import com.petexpress.israel.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("task")
@Tag(name = "Tasks")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Buscar todas as tasks")
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Adicionar uma task a um animal")
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @Operation(summary = "Buscar Task por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task encontrada"),
            @ApiResponse(responseCode = "404", description = "Task não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Atualiza Task por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task atualizada"),
            @ApiResponse(responseCode = "404", description = "Task não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable UUID id, @RequestBody @Valid TaskUpdateDto dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @Operation(summary = "Deletar task por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
