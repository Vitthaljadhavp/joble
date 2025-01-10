package com.joble.joble.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.joble.joble.model.Job;
import com.joble.joble.model.User;
import com.joble.joble.repository.JobRepository;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;

    @PostMapping
    public ResponseEntity<String> postJob(@RequestBody Job job, @AuthenticationPrincipal User employer) {
        if (employer == null || !employer.getRole().equals("EMPLOYER")) {
            return ResponseEntity.status(403).body("Only employers can post jobs!");
        }
        job.setEmployer(employer);
        jobRepository.save(job);
        return ResponseEntity.ok("Job posted successfully!");
    }

    @GetMapping
    public List<Job> searchJobs(@RequestParam(required = false) String title,
                                @RequestParam(required = false) String location) {
        return jobRepository.findByTitleContainingOrLocationContaining(title, location);
    }
}
