package com.joble.joble.service;

import com.joble.joble.model.Job;
import com.joble.joble.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Create a new job posting
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all job postings
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Search for jobs by title or location
    public List<Job> searchJobs(String title, String location) {
        return jobRepository.findByTitleContainingOrLocationContaining(title, location);
    }

    // Get job by ID
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

}
