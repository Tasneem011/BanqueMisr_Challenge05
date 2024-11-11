package com.example.banquemisr.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class history {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private LocalDateTime timestamp;

    @ManyToOne
    private Task task;
}
