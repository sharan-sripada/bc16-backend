package com.greencommute.organizationservice.controller;

import com.greencommute.organizationservice.dto.OrganisationDTO;
import com.greencommute.organizationservice.entity.Organisation;
import com.greencommute.organizationservice.service.OrganisationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganisationController.class)
@ExtendWith(SpringExtension.class)
class OrganisationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganisationService organisationService;

    @Test
    void getOrganisation() throws Exception {
        List<OrganisationDTO> list = new ArrayList<>();

        OrganisationDTO newOrganisation = new
                OrganisationDTO(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);
        Organisation organisation1 = new
                Organisation(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);

        list.add(newOrganisation);

        when(organisationService.findAll()).thenReturn(list);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/api/v1/organizations")).andExpect(status().isOk()).andReturn();

        verify(organisationService, times(1)).findAll();
    }

    @Test
    void getOrganisationById() throws Exception {

        OrganisationDTO newOrganisation = new
                OrganisationDTO(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);
        Organisation organisation1 = new
                Organisation(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);


        when(organisationService.findById(newOrganisation.getId()))
                .thenReturn(newOrganisation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                "/api/v1/organizations/"+ newOrganisation.getId())).andExpect(status().isOk()).andReturn();

    }
}