package com.greencommute.jobservice.rest;

import com.greencommute.jobservice.dto.JobPostDTO;
import com.greencommute.jobservice.dto.SaveJobsDTO;
import com.greencommute.jobservice.service.JobPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class JobPostController {

    @Autowired
    JobPostService jobPostService;

    @GetMapping("/jobs")
    public List<JobPostDTO> getAllJobs(@RequestParam(required = false, name = "city") List<String> cities,
            @RequestParam(required = false, name = "jobTitle") String jobTitle) {

        if (cities != null && jobTitle != null) {

            return jobPostService.getJobsByCityAndOrgIds(cities, jobTitle);
        }

        return jobPostService.getAllJobPosts();
    }

    @GetMapping("/jobs/{jobId}")
    public JobPostDTO getJobPost(@PathVariable UUID jobId) {
        return jobPostService.getJobPostById(jobId);
    }

    @PostMapping("/jobs/{jobId}/save")
    public SaveJobsDTO saveJob(@RequestBody SaveJobsDTO saveJobDTO, @PathVariable(name = "jobId") UUID jobId) {

        // Get the job details based on ID
        JobPostDTO jobPostDTO = jobPostService.getJobPostById(jobId);
        saveJobDTO.setJobPost(jobPostDTO);

        return jobPostService.saveJob(saveJobDTO);

    }

    @GetMapping("/jobs/saved")
    public List<SaveJobsDTO> getSavedJobs() {
        return jobPostService.getSavedJobs();
    }

    @DeleteMapping("/jobs/saved/{saveJobId}")
    public void deleteSavedJob(@PathVariable(name = "saveJobId") UUID saveJobId) {
        jobPostService.deleteSavedJob(saveJobId);
    }
}
