package com.example.banquemisr.Service;

import com.example.banquemisr.Model.Task;
import com.example.banquemisr.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    ApiResponse createTask(Task task);
    ApiResponse getTaskById(Long id);
    Page<Task> getAllTasks(Pageable pageable);
    ApiResponse updateTask(Task task, Long id);
    void deleteTask(Long id);
    Page<org.springframework.scheduling.config.Task> searchTasks(String title, String description, String status, Pageable pageable);

    List<Task> findTasksWithUpcomingDeadlines(LocalDateTime now);

    Task updateTaskStatus(Long taskId, String status);
}
