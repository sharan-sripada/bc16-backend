package com.greencommute.jobservice.repository;

import com.greencommute.jobservice.entity.SaveJobs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SaveJobsRepository extends JpaRepository<SaveJobs, UUID> {
}
