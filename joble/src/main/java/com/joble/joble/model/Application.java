package com.joble.joble.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private User seeker;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status; // Enum for better status management

    // Getters and Setters
    
}
