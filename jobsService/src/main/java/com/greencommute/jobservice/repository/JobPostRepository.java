package com.greencommute.jobservice.repository;

import com.greencommute.jobservice.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    List<JobPost> findByJobTitleAndOrgIdIn(String jobTitle, List<UUID> orgIds);
}
