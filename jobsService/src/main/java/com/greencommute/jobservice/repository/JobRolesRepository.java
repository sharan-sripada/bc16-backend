package com.greencommute.jobservice.repository;

import com.greencommute.jobservice.entity.JobRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRolesRepository extends JpaRepository<JobRoles, UUID> {
}
