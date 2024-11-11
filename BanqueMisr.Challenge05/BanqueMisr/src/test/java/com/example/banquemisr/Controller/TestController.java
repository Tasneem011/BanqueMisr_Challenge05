package com.example.banquemisr.Controller;

import com.example.banquemisr.Model.Task;
import com.example.banquemisr.Service.TaskService;
import com.example.banquemisr.dto.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static jdk.jfr.internal.jfc.model.Constraint.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestController {
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void createTaskTest() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        ApiResponse response = new ApiResponse(true, "Task created", task);

        when(taskService.createTask(any(Task.class))).thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"title\": \"Test Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task created"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void getTaskTest() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        ApiResponse response = new ApiResponse(true, "Task found", task);

        when(taskService.getTaskById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task found"))
                .andExpect(jsonPath("$.data.title").value("Test Task"));

        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void getTasksTest() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        List<Task> tasks = Collections.singletonList(task);
        Page<Task> page = new PageImpl<>(tasks);

        when(taskService.getAllTasks(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/tasks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService, times(1)).getAllTasks(any(PageRequest.class));
    }

    @Test
    void updateTaskTest() throws Exception {
        Task task = new Task();
        task.setTitle("Updated Task");
        ApiResponse response = new ApiResponse(true, "Task updated successfully", task);

        when(taskService.updateTask(any(Task.class), eq(1L))).thenReturn(response);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task updated successfully"));

        verify(taskService, times(1)).updateTask(any(Task.class), eq(1L));
    }

    @Test
    void deleteTaskTest() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void searchTasksTest() throws Exception {
        Task task = new Task();
        task.setTitle("Search Task");
        List<Task> tasks = Collections.singletonList(task);
        Page<Task> page = new PageImpl<>(tasks);

        when(taskService.searchTasks(anyString(), anyString(), anyString(), any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/tasks/search")
                        .param("title", "Search Task")
                        .param("description", "Description")
                        .param("status", "Open")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Search Task"));

        verify(taskService, times(1)).searchTasks(anyString(), anyString(), anyString(), any(PageRequest.class));
    }
}
}
