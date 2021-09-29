package com.greencommute.organizationservice.exception;

import com.greencommute.organizationservice.controller.OrganisationController;
import com.greencommute.organizationservice.service.OrganisationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganisationController.class)
class CustomExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganisationService organisationService;

    @Test
    void handleImageNotFoundException() throws Exception {
        UUID exceptionParam = UUID.fromString("18f8283f-baf8-474d-9ba7-dd303af32e10");

        when(organisationService.findById(exceptionParam)).thenThrow(new ResourceNotFoundException("Id Not Found."));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1//organizations/"+ exceptionParam,
                exceptionParam)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Id Not Found.", result.getResolvedException().getMessage()));
    }

    @Test
    void HandleException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1//organizations123/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}



