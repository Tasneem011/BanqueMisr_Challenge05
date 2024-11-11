package com.example.banquemisr.Service;

import com.example.banquemisr.Exceptions.ResourceNotFoundException;
import com.example.banquemisr.Model.Task;
import com.example.banquemisr.Repository.TaskRepository;
import com.example.banquemisr.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public ApiResponse createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        return new ApiResponse(true, "Task created", savedTask);
    }

    @Override
    public ApiResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        return new ApiResponse(true, "Task found", task);
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public ApiResponse updateTask(Task task, Long id) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());

        Task updatedTask = taskRepository.save(existingTask);
        return new ApiResponse(true, "Task updated successfully", updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        taskRepository.delete(task);
    }

    @Override
    public Page<Task> searchTasks(String title, String description, String status, Pageable pageable) {
        return taskRepository.findByTitleContainingAndDescriptionContainingAndStatus(
                title != null ? title : "",
                description != null ? description : "",
                status != null ? status : "",
                pageable
        );
    }
    @Override
    public Task updateTaskStatus(Long taskId, String status) {
        Task task = getTaskById(taskId);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    // Optional method for fetching tasks by status (e.g., to list completed tasks)
    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }
}
