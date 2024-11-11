package com.example.banquemisr.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTitleContainingAndDescriptionContainingAndStatus(
            String title, String description, String status, Pageable pageable);
    List<Task> findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    // Query tasks by user ID (assumes a 'user' field in Task)
    List<Task> findByUserId(Long userId);

    // Query tasks by their status (e.g., "completed", "in-progress")
    List<Task> findByStatus(String status);
}
