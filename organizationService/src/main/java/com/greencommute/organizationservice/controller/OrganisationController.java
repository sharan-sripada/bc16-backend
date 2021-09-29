package com.greencommute.organizationservice.controller;

import com.greencommute.organizationservice.dto.OrganisationDTO;
import com.greencommute.organizationservice.exception.ResourceNotFoundException;
import com.greencommute.organizationservice.service.OrganisationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class OrganisationController {

    private final OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService){
        this.organisationService=organisationService;
    }

    //get Organisation
    @GetMapping("organizations")

    public List<OrganisationDTO> getAllOrganisation(
            @RequestParam(required = false,name="city") List<String> city

    ){
        if(city!=null){
            return this.organisationService.findByCity(city);
        }
        return this.organisationService.findAll();
    }

    //get Organisation By id
    @GetMapping("/organizations/{id}")
    public ResponseEntity<OrganisationDTO> getOrganisationById(@PathVariable(value = "id") UUID organisationId)
            throws ResourceNotFoundException {
        OrganisationDTO organisation = organisationService.findById(organisationId);
        return new ResponseEntity<>(organisation, new HttpHeaders(), HttpStatus.OK);
    }
}


