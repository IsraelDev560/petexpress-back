package com.petexpress.israel.service;

import com.petexpress.israel.dto.update.TaskUpdateDto;
import com.petexpress.israel.entities.Task;
import com.petexpress.israel.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllServices() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(UUID id){
        return taskRepository.findById(id);
    }

    @Transactional
    public Task updateTask(UUID id, TaskUpdateDto dto) throws ChangeSetPersister.NotFoundException {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        task.setTask(dto.getTask());
        return taskRepository.save(task);
    }

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
