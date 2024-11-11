package com.example.banquemisr.Controller;

import com.example.banquemisr.Model.Task;
import com.example.banquemisr.Service.TaskService;
import com.example.banquemisr.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ApiResponse createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public ApiResponse getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public Page<Task> getTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @PutMapping("/{id}")
    public ApiResponse updateTask(@RequestBody Task task, @PathVariable Long id) {
        return taskService.updateTask(task, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/search")
    public Page<Task> searchTasks(@RequestParam String title, @RequestParam String description,
                                  @RequestParam String status, Pageable pageable) {
        return taskService.searchTasks(title, description, status, pageable);
    }
}
