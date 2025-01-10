package com.joble.joble.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.joble.joble.model.Job;
import com.joble.joble.model.User;
import com.joble.joble.repository.JobRepository;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Post a new job (Only EMPLOYER can post jobs)
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public ResponseEntity<String> postJob(@RequestBody @Valid Job job, @AuthenticationPrincipal User employer) {
        if (employer == null || !employer.getRole().equals("EMPLOYER")) {
            return ResponseEntity.status(403).body("Only employers can post jobs!");
        }
        job.setEmployer(employer);  // Assign the employer to the job
        jobRepository.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).body("Job posted successfully!");
    }

    // Search for jobs by title or location
    @GetMapping
    public ResponseEntity<List<Job>> searchJobs(@RequestParam(required = false) String title,
                                                @RequestParam(required = false) String location) {
        List<Job> jobs = jobRepository.findByTitleContainingOrLocationContaining(title, location);
        return ResponseEntity.ok(jobs);
    }

    // Get job details by job ID (This can be added if needed)
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(job -> ResponseEntity.ok(job))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
