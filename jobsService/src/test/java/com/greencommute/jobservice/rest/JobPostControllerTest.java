package com.greencommute.jobservice.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greencommute.jobservice.dto.*;
import com.greencommute.jobservice.service.JobPostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobPostController.class)
@ExtendWith(SpringExtension.class)
class JobPostControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private JobPostService jobPostService;

        @Autowired
        ObjectMapper objectMapper;

        @Test
        void getAllJobs() throws Exception {

                List<JobPostDTO> jobPosts = new ArrayList<>();
                String[] req = { "Sample requirements", "dummy req" };

                JobPostDTO jobPost = new JobPostDTO(UUID.randomUUID(), "UX Designer", UUID.randomUUID(), "dummy",
                                "MID_LEVEL", "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(),
                                new OrganizationPOJO(), false);
                jobPosts.add(jobPost);

                when(jobPostService.getAllJobPosts()).thenReturn(jobPosts);
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/jobs"))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).getAllJobPosts();

        }

        @Test
        void getAllJobsWithParams() throws Exception {

                List<JobPostDTO> jobPosts = new ArrayList<>();
                String[] req = { "Sample requirements", "dummy req" };

                JobPostDTO jobPost = new JobPostDTO(UUID.randomUUID(), "UX Designer", UUID.randomUUID(), "dummy",
                                "MID_LEVEL", "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(),
                                new OrganizationPOJO(),false);
                jobPosts.add(jobPost);

                List<String> city = new ArrayList<>();
                city.add("Hyderabad");

                when(jobPostService.getJobsByCityAndOrgIds(anyList(), anyString())).thenReturn(jobPosts);
                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.get("/api/v1/jobs?city=Hyderabad&jobTitle=UX Designer"))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).getJobsByCityAndOrgIds(city, "UX " + "Designer");

        }

        @Test
        void getJobPost() throws Exception {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                JobPostDTO jobPost = new JobPostDTO(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(), new OrganizationPOJO(),false);

                when(jobPostService.getJobPostById(id)).thenReturn(jobPost);
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/jobs/" + id))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).getJobPostById(id);

        }

        @Test
        void saveJobTest() throws Exception {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                AddressPOJO addressPOJO = new AddressPOJO();
                addressPOJO.setAddressLine1("Dummy");
                addressPOJO.setAddressLine2("Dummy");
                addressPOJO.setCity("Hyd");
                addressPOJO.setId(UUID.randomUUID());
                addressPOJO.setCountry("IN");
                addressPOJO.setState("YB");
                addressPOJO.setLocation("Dummy");
                addressPOJO.setZipcode("123");
                AddressPOJO[] adds = { addressPOJO };
                OrganizationPOJO org = new OrganizationPOJO(UUID.randomUUID(), "Dummy", "url", "Dummy", "URL", "dum",
                                adds);
                org.setAddresses(adds);

                JobPostDTO jobPost = new JobPostDTO(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(), org,false);

                SaveJobsDTO saveJobsDTO = new SaveJobsDTO();
                saveJobsDTO.setJobPost(jobPost);
                saveJobsDTO.setUserId(id);

                when(jobPostService.saveJob(saveJobsDTO)).thenReturn(saveJobsDTO);

                MvcResult result = mockMvc
                                .perform(MockMvcRequestBuilders.post("/api/v1/jobs/" + id + "/save")
                                                .accept(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(saveJobsDTO))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).saveJob(any(SaveJobsDTO.class));

        }

        @Test
        void getSavedJobsTest() throws Exception {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                JobPostDTO jobPost = new JobPostDTO(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(), new OrganizationPOJO(),false);

                SaveJobsDTO saveJobsDTO = new SaveJobsDTO();
                saveJobsDTO.setJobPost(jobPost);
                saveJobsDTO.setUserId(id);
                List<SaveJobsDTO> saveJobsDTOS = new ArrayList<>();
                saveJobsDTOS.add(saveJobsDTO);

                when(jobPostService.getSavedJobs()).thenReturn(saveJobsDTOS);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/jobs/saved"))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).getSavedJobs();
        }

        @Test
        void deleteSavedJobTest() throws Exception {

                String[] req = { "Sample requirements", "dummy req" };
                UUID id = UUID.randomUUID();

                AddressPOJO addressPOJO = new AddressPOJO();
                addressPOJO.setAddressLine1("Dummy");
                addressPOJO.setAddressLine2("Dummy");
                addressPOJO.setCity("Hyd");
                addressPOJO.setId(UUID.randomUUID());
                addressPOJO.setCountry("IN");
                addressPOJO.setState("YB");
                addressPOJO.setLocation("Dummy");
                addressPOJO.setZipcode("123");
                AddressPOJO[] adds = { addressPOJO };
                OrganizationPOJO org = new OrganizationPOJO(UUID.randomUUID(), "Dummy", "url", "Dummy", "URL", "dum",
                                adds);
                org.setAddresses(adds);

                JobPostDTO jobPost = new JobPostDTO(id, "UX Designer", UUID.randomUUID(), "dummy", "MID_LEVEL",
                                "FULL_TIME", true, req, UUID.randomUUID(), new JobRolesDTO(), org,false);

                SaveJobsDTO saveJobsDTO = new SaveJobsDTO();
                saveJobsDTO.setJobPost(jobPost);
                saveJobsDTO.setUserId(id);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/jobs/saved/" + id))
                                .andExpect(status().isOk()).andReturn();

                verify(jobPostService, times(1)).deleteSavedJob(id);

        }
}