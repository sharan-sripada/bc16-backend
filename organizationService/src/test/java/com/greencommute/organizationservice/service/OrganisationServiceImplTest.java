package com.greencommute.organizationservice.service;

import com.greencommute.organizationservice.dao.OrganisationRepository;
import com.greencommute.organizationservice.dto.OrganisationDTO;
import com.greencommute.organizationservice.entity.Address;
import com.greencommute.organizationservice.entity.Organisation;
import com.greencommute.organizationservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrganisationServiceImplTest {

    @TestConfiguration
    static class JobPostServiceImplTestContextConfiguration {

        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        public OrganisationServiceImpl organisationService() {
            return new OrganisationServiceImpl();
        }
    }

    @MockBean
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationService organisationService;

    @Test
    void findAll() {
        List<Organisation> list = new ArrayList<>();

        Organisation newOrganisation = new
                Organisation(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);

        list.add(newOrganisation);

        when(organisationRepository.findAll()).thenReturn(list);

        List<OrganisationDTO> result = organisationService.findAll();
        assertEquals(1, result.size());
        verify(organisationRepository, times(1)).findAll();
    }

    @Test
    void findById() throws ResourceNotFoundException {
        Organisation newOrganisation = new
                Organisation(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);

        when(organisationRepository.findById(newOrganisation.getId()))
                .thenReturn(java.util.Optional.of(newOrganisation));

        OrganisationDTO organisationDTO = organisationService.findById(newOrganisation.getId());
        verify(organisationRepository, times(1)).findById(newOrganisation.getId());

    }

    @Test
    void findByCity() {
        List<Organisation> list =  new ArrayList<>();

        Organisation newOrganisation = new
                Organisation(UUID.fromString("5532b270-3337-47ec-b3ae-d5814c014f15"),"BMW","www.bmw.com","Bmw",
                "www.bmw.com","2004-10-19 13:53:54+05:30",null);

        Address newAddress = new Address(UUID.fromString("2fb653de-ccb6-4353-ba35-f81d13287a70"), "Tlck", "Shaikpet", "Vijayawada", "AP", "India",
                "50009", "(3,4)",newOrganisation);

        list.add(newOrganisation);
        List<String> cities = new ArrayList<>();
        cities.add("Hyderabad");
        cities.add("Vijayawada");

        when(organisationRepository.findByCity(cities)).thenReturn(list);

        List<OrganisationDTO> result = organisationService.findByCity(cities);
        verify(organisationRepository,times(1)).findByCity(cities);
        assertEquals(1,result.size());

    }
}