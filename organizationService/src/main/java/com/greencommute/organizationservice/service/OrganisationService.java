package com.greencommute.organizationservice.service;

import com.greencommute.organizationservice.dto.OrganisationDTO;
import com.greencommute.organizationservice.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;

public interface OrganisationService {
    /**
     * Get all Organisations
     *
     * @return all organisation list.
     */
    List<OrganisationDTO> findAll();

    /**
     * Get single Organisation using Id
     * @param organisationId
     * @return Organisation based on id.
     * @throws ResourceNotFoundException
     */
    OrganisationDTO findById(UUID organisationId) throws ResourceNotFoundException;

    /**
     * Get Organisation by using city
     * @param city
     * @return filtered organisation list based on query params.
     */
    List<OrganisationDTO> findByCity(List<String> city);
}
