package com.joble.joble.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joble.joble.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByTitleContainingOrLocationContaining(String title, String location);
}
