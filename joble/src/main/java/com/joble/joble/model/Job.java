package com.joble.joble.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Job description is required")
    @Size(max = 1000, message = "Job description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Skills required is required")
    private String skillsRequired;

    @NotBlank(message = "Job location is required")
    private String location;

    @ManyToOne
    @JoinColumn(name = "employer_id") // This will map the employer ID as a foreign key
    @JsonIgnore // Optional: Prevents circular references during serialization
    private User employer;

}
