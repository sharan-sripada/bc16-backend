package com.greencommute.jobservice.service;

import com.greencommute.jobservice.dto.JobPostDTO;
import com.greencommute.jobservice.dto.SaveJobsDTO;

import java.util.List;
import java.util.UUID;

public interface JobPostService {
    /**
     * Get all jobs.
     * 
     * @return all jobs list.
     */
    List<JobPostDTO> getAllJobPosts();

    /**
     * Perform fetching jobs operation by using cityName & job title.
     * 
     * @param cityNames is used for filter jobs by respective cities.
     * @param jobTitle  is used for filter jobs by title.
     * @return filterd jobs list based on query params.
     */
    List<JobPostDTO> getJobsByCityAndOrgIds(List<String> cityNames, String jobTitle);

    /**
     * Perform single job fetching operation.
     * 
     * @param uuid is used for fetch job using job id.
     * @return job based on query id.
     */

    JobPostDTO getJobPostById(UUID jobId);

    /**
     * Perform single job fetching operation.
     * 
     * @param SaveJobsDTO model used to store job. request body should be like this
     *                    { "job_id": "some_id" , "user_id": "some_id"}
     * @return job based on query id.
     */
    SaveJobsDTO saveJob(SaveJobsDTO saveJobDTO);

    /**
     * Get all save jobs.
     * 
     * @return all saved jobs list.
     */
    List<SaveJobsDTO> getSavedJobs();

    /**
     * delete saved job.
     */
    void deleteSavedJob(UUID saveJobId);
}
