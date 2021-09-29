package com.greencommute.jobservice.service.impl;

import com.greencommute.jobservice.dto.JobPostDTO;
import com.greencommute.jobservice.dto.OrganizationPOJO;
import com.greencommute.jobservice.dto.SaveJobsDTO;
import com.greencommute.jobservice.entity.JobPost;
import com.greencommute.jobservice.entity.SaveJobs;
import com.greencommute.jobservice.exception.ResourceNotFoundException;
import com.greencommute.jobservice.repository.JobPostRepository;
import com.greencommute.jobservice.repository.SaveJobsRepository;
import com.greencommute.jobservice.service.JobPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobPostServiceImpl implements JobPostService {

    @Autowired
    private ModelMapper modelMapper;

    @Value("${ORG_URL}")
    String orgUrl;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private SaveJobsRepository saveJobsRepository;

    @Autowired
    private RestTemplate restTemplate;

    private JobPostDTO convertToDto(JobPost job) {

        return modelMapper.map(job, JobPostDTO.class);
    }

    @Override
    public List<JobPostDTO> getAllJobPosts() {
        return jobPostRepository.findAll().stream().map(jobPost -> {

            JobPostDTO jobPostDTO = convertToDto(jobPost);
            // Update org info of each saved job
            ResponseEntity<OrganizationPOJO> responseEntity = getMatchingOrgsById(jobPostDTO.getOrgId());
            OrganizationPOJO orgs = responseEntity.getBody();
            jobPostDTO.setOrganization(orgs);
            jobPostDTO.setSaved(isJobSaved(jobPost.getId()));
            return jobPostDTO;

        }).collect(Collectors.toList());
    }

    @Override
    public List<JobPostDTO> getJobsByCityAndOrgIds(List<String> cityNames, String jobTitle) {

        // Get the org ids matching with city name
        ResponseEntity<List<OrganizationPOJO>> responseEntity = getMatchingOrgsByCity(cityNames);

        List<OrganizationPOJO> orgs = responseEntity.getBody();

        assert orgs != null;
        List<UUID> orgIds = orgs.stream().map(OrganizationPOJO::getId).collect(Collectors.toList());

        List<JobPostDTO> jobPostDTOS = jobPostRepository.findByJobTitleAndOrgIdIn(jobTitle, orgIds).stream()
                .map(this::convertToDto).collect(Collectors.toList());

        // add the org info to job posts
        combineJobsWithOrgs(orgs, jobPostDTOS);

        return jobPostDTOS;
    }

    private void combineJobsWithOrgs(List<OrganizationPOJO> orgs, List<JobPostDTO> jobPostDTOS) {
        for (OrganizationPOJO org : orgs) {
            for (JobPostDTO jobPostDTO : jobPostDTOS) {
                if (org.getId().equals(jobPostDTO.getOrgId())) {
                    jobPostDTO.setOrganization(org);
                    jobPostDTO.setSaved(isJobSaved(jobPostDTO.getId()));
                }
            }
        }
    }

    private ResponseEntity<List<OrganizationPOJO>> getMatchingOrgsByCity(List<String> cityNames) {
        Map<String, String> params = new HashMap<>();
        params.put("city", String.join(",", cityNames));

        return restTemplate.exchange(orgUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<OrganizationPOJO>>() {
                }, params);

    }

    public boolean isJobSaved(UUID job_id) {
        final boolean[] isFound = { false };
        List<SaveJobs> jobs = saveJobsRepository.findAll();
        jobs.forEach(saveJob -> {
            if (saveJob.getJobPost().getId() == job_id) {
                isFound[0] = true;
            }
        });
        return isFound[0];
    }

    @Override
    public JobPostDTO getJobPostById(UUID jobId) {
        JobPostDTO jobPostDTO = null;

        Optional<JobPost> result = jobPostRepository.findById(jobId);

        if (result.isPresent()) {
            jobPostDTO = convertToDto(result.get());
        } else {
            throw new ResourceNotFoundException("Did not find the Job with id - " + jobId);
        }

        // update the org info of jobPost
        ResponseEntity<OrganizationPOJO> responseEntity = getMatchingOrgsById(jobPostDTO.getOrgId());

        OrganizationPOJO orgs = responseEntity.getBody();
        jobPostDTO.setOrganization(orgs);
        return jobPostDTO;
    }

    private ResponseEntity<OrganizationPOJO> getMatchingOrgsById(UUID orgId) {

        return restTemplate.exchange(orgUrl + "/" + orgId, HttpMethod.GET, null,
                new ParameterizedTypeReference<OrganizationPOJO>() {
                });

    }

    @Override
    public SaveJobsDTO saveJob(SaveJobsDTO saveJobDTO) {

        SaveJobs saveJobs = modelMapper.map(saveJobDTO, SaveJobs.class);
        saveJobs = saveJobsRepository.save(saveJobs);
        saveJobDTO.setId(saveJobs.getId());
        return saveJobDTO;
    }

    @Override
    public List<SaveJobsDTO> getSavedJobs() {

        return saveJobsRepository.findAll().stream().map(saveJobs -> {

            SaveJobsDTO saveJobsDTO = modelMapper.map(saveJobs, SaveJobsDTO.class);
            // Update org info of each saved job
            ResponseEntity<OrganizationPOJO> responseEntity = getMatchingOrgsById(saveJobs.getJobPost().getOrgId());
            OrganizationPOJO orgs = responseEntity.getBody();
            saveJobsDTO.getJobPost().setOrganization(orgs);

            return saveJobsDTO;
        }).collect(Collectors.toList());

    }

    @Override
    public void deleteSavedJob(UUID saveJobId) {
        saveJobsRepository.deleteById(saveJobId);
    }
}
