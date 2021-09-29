package com.greencommute.jobservice.service.impl;

import com.greencommute.jobservice.dto.AddressPOJO;
import com.greencommute.jobservice.dto.JobPostDTO;
import com.greencommute.jobservice.dto.SaveJobsDTO;
import com.greencommute.jobservice.entity.JobRoles;
import com.greencommute.jobservice.dto.OrganizationPOJO;
import com.greencommute.jobservice.entity.JobPost;
import com.greencommute.jobservice.entity.SaveJobs;
import com.greencommute.jobservice.exception.ResourceNotFoundException;
import com.greencommute.jobservice.repository.JobPostRepository;
import com.greencommute.jobservice.repository.SaveJobsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JobPostServiceImplTest {

        @Value("${ORG_URL}")
        String orgUrl;

        @TestConfiguration
        static class JobPostServiceImplTestContextConfiguration {

                @Bean
                public ModelMapper modelMapper() {
                        return new ModelMapper();
                }

                @Bean
                public JobPostServiceImpl jobPostService() {
                        return new JobPostServiceImpl();
                }
        }

        @MockBean
        private RestTemplate restTemplate;

        @Autowired
        private JobPostServiceImpl jobPostService;

        @MockBean
        private JobPostRepository jobPostRepository;

        @MockBean
        private SaveJobsRepository saveJobsRepository;

        @Test
        void getAllJobPosts() {

                AddressPOJO addressPOJO = new AddressPOJO(UUID.fromString("e0b3e487" + "-3c27-48d7-85e1-de91c76240e7"),
                                "dummy", "dumm2", "Hyderabad", "TN", "IN", "8787", "17.3850-78.4867");
                AddressPOJO[] addressPOJOS = { addressPOJO };

                UUID orgId = UUID.fromString("84a44e44-d9af-48f2-9785-95967bcadc6e");

                OrganizationPOJO organizationPOJO = new OrganizationPOJO(orgId, "HP", "Dummy", "Dummy", "dummy",
                                "dummy", addressPOJOS);

                List<JobPost> jobPosts = new ArrayList<>();
                String[] req = { "Sample requirements", "dummy req" };

                JobPost jobPost = new JobPost(UUID.randomUUID(), "UX Designer", orgId, "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, new JobRoles(), orgId);
                jobPosts.add(jobPost);

                when(restTemplate.exchange(orgUrl + "/" + orgId, HttpMethod.GET, null,
                                new ParameterizedTypeReference<OrganizationPOJO>() {
                                })).thenReturn(new ResponseEntity<>(organizationPOJO, HttpStatus.OK));
                when(jobPostRepository.findAll()).thenReturn(jobPosts);

                assertEquals(1, jobPostService.getAllJobPosts().size());
        }

        @Test
        void getJobsByCityAndOrgIds() {

                List<JobPost> jobPosts = new ArrayList<>();
                String[] req = { "Sample requirements", "dummy req" };

                JobPost jobPost = new JobPost(UUID.randomUUID(), "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, new JobRoles(), UUID.randomUUID());
                jobPosts.add(jobPost);

                List<String> cities = new ArrayList<>();
                cities.add("Hyderabad");

                when(jobPostRepository.findByJobTitleAndOrgIdIn(any(), any())).thenReturn(jobPosts);

                Map<String, String> params = new HashMap<>();
                params.put("city", "Hyderabad");

                List<OrganizationPOJO> organizationPOJOS = new ArrayList<>();

                AddressPOJO addressPOJO = new AddressPOJO(UUID.fromString("e0b3e487" + "-3c27-48d7-85e1-de91c76240e7"),
                                "dummy", "dumm2", "Hyderabad", "TN", "IN", "8787", "17.3850-78.4867");
                AddressPOJO[] addressPOJOS = { addressPOJO };

                UUID orgId = UUID.fromString("84a44e44-d9af-48f2-9785-95967bcadc6e");

                OrganizationPOJO organizationPOJO = new OrganizationPOJO(orgId, "HP", "Duumy", "DUmmy", "dummy",
                                "dummy", addressPOJOS);
                organizationPOJOS.add(organizationPOJO);

                when(restTemplate.exchange(orgUrl, HttpMethod.GET, null,
                                new ParameterizedTypeReference<List<OrganizationPOJO>>() {
                                }, params)).thenReturn(new ResponseEntity<>(organizationPOJOS, HttpStatus.OK));

                List<JobPostDTO> res = jobPostService.getJobsByCityAndOrgIds(cities, "UI Developer");

                List<UUID> orgs = new ArrayList<>();
                orgs.add(orgId);

                assertEquals(1, res.size());
                verify(jobPostRepository, times(1)).findByJobTitleAndOrgIdIn("UI " + "Developer", orgs);

        }

        @Test
        void getJobPostById() {

                UUID id = UUID.randomUUID();

                AddressPOJO addressPOJO = new AddressPOJO(UUID.fromString("e0b3e487" + "-3c27-48d7-85e1-de91c76240e7"),
                                "dummy", "dumm2", "Hyderabad", "TN", "IN", "8787", "17.3850-78.4867");
                AddressPOJO[] addressPOJOS = { addressPOJO };

                UUID orgId = UUID.fromString("84a44e44-d9af-48f2-9785-95967bcadc6e");

                OrganizationPOJO organizationPOJO = new OrganizationPOJO(orgId, "HP", "Dummy", "Dummy", "dummy",
                                "dummy", addressPOJOS);

                String[] req = { "Sample requirements", "dummy req" };

                JobPost jobPost = new JobPost(id, "UX Designer", orgId, "dummy", "MID_LEVEL", "FULL_TIME", true, req,
                                new JobRoles(), orgId);

                when(restTemplate.exchange(orgUrl + "/" + orgId, HttpMethod.GET, null,
                                new ParameterizedTypeReference<OrganizationPOJO>() {
                                })).thenReturn(new ResponseEntity<>(organizationPOJO, HttpStatus.OK));

                when(jobPostRepository.findById(id)).thenReturn(Optional.of(jobPost));
                JobPostDTO jobPostDTO = jobPostService.getJobPostById(id);
                verify(jobPostRepository, times(1)).findById(id);
        }

        @Test
        void getJobPostByIdThrowsException() {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                JobPost jobPost = new JobPost(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL", "FULL_TIME",
                                true, req, new JobRoles(), UUID.randomUUID());

                when(jobPostRepository.findById(id)).thenReturn(Optional.empty());

                try {
                        JobPostDTO jobPostDTO = jobPostService.getJobPostById(id);
                } catch (ResourceNotFoundException expected) {
                        assertEquals("Did not find the Job with id - " + id, expected.getMessage());
                }
        }

        @Test
        void saveJobTest() {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                JobPost jobPost = new JobPost(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL", "FULL_TIME",
                                true, req, new JobRoles(), UUID.randomUUID());

                SaveJobs saveJobs = new SaveJobs();
                saveJobs.setJobPost(jobPost);
                saveJobs.setId(id);

                SaveJobsDTO saveJobsDTO = new SaveJobsDTO();
                saveJobsDTO.setId(id);

                when(saveJobsRepository.save(any(SaveJobs.class))).thenReturn(saveJobs);
                saveJobsDTO = jobPostService.saveJob(saveJobsDTO);
                verify(saveJobsRepository, times(1)).save(any(SaveJobs.class));

        }

        @Test
        void getSavedJobsTest() {

                UUID id = UUID.randomUUID();

                AddressPOJO addressPOJO = new AddressPOJO(UUID.fromString("e0b3e487" + "-3c27-48d7-85e1-de91c76240e7"),
                                "dummy", "dumm2", "Hyderabad", "TN", "IN", "8787", "17.3850-78.4867");
                AddressPOJO[] addressPOJOS = { addressPOJO };

                UUID orgId = UUID.fromString("84a44e44-d9af-48f2-9785-95967bcadc6e");

                OrganizationPOJO organizationPOJO = new OrganizationPOJO(orgId, "HP", "Dummy", "Dummy", "dummy",
                                "dummy", addressPOJOS);

                String[] req = { "Sample requirements", "dummy req" };

                JobPost jobPost = new JobPost(id, "UX Designer", orgId, "dummy", "MID_LEVEL", "FULL_TIME", true, req,
                                new JobRoles(), orgId);

                when(restTemplate.exchange(orgUrl + "/" + orgId, HttpMethod.GET, null,
                                new ParameterizedTypeReference<OrganizationPOJO>() {
                                })).thenReturn(new ResponseEntity<>(organizationPOJO, HttpStatus.OK));

                SaveJobs saveJobs = new SaveJobs(id, jobPost, id);
                List<SaveJobs> saveJobsList = new ArrayList<>();
                saveJobsList.add(saveJobs);

                when(saveJobsRepository.findAll()).thenReturn(saveJobsList);
                List<SaveJobsDTO> saveJobsDTOS = jobPostService.getSavedJobs();
                verify(saveJobsRepository, times(1)).findAll();
                assertEquals(1, saveJobsDTOS.size());

        }

        @Test
        void deleteSavedJobTest() {

                UUID id = UUID.randomUUID();

                AddressPOJO addressPOJO = new AddressPOJO(UUID.fromString("e0b3e487" + "-3c27-48d7-85e1-de91c76240e7"),
                                "dummy", "dumm2", "Hyderabad", "TN", "IN", "8787", "17.3850-78.4867");
                AddressPOJO[] addressPOJOS = { addressPOJO };

                UUID orgId = UUID.fromString("84a44e44-d9af-48f2-9785-95967bcadc6e");

                OrganizationPOJO organizationPOJO = new OrganizationPOJO(orgId, "HP", "Dummy", "Dummy", "dummy",
                                "dummy", addressPOJOS);

                String[] req = { "Sample requirements", "dummy req" };

                JobPost jobPost = new JobPost(id, "UX Designer", orgId, "dummy", "MID_LEVEL", "FULL_TIME", true, req,
                                new JobRoles(), orgId);

                SaveJobs saveJobs = new SaveJobs(id, jobPost, id);
                jobPostService.deleteSavedJob(id);
                verify(saveJobsRepository, times(1)).deleteById(id);
        }

}