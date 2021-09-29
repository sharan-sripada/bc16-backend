package com.greencommute.organizationservice.service;

import com.greencommute.organizationservice.dao.OrganisationRepository;
import com.greencommute.organizationservice.dto.OrganisationDTO;
import com.greencommute.organizationservice.entity.Organisation;
import com.greencommute.organizationservice.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrganisationServiceImpl implements OrganisationService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Override
    @Transactional
    public List<OrganisationDTO> findAll() {

        return organisationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrganisationDTO findById(UUID organisationId) throws ResourceNotFoundException {
        Optional<Organisation>  result = organisationRepository.findById(organisationId);
        if (result.isPresent()) {
            return convertToDTO(result.get());
        }
        throw new ResourceNotFoundException("Organisation not found for this id"+ organisationId);
    }

    @Override
    @Transactional
    public List<OrganisationDTO> findByCity(List<String> city) {
        return organisationRepository.findByCity(city).stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrganisationDTO convertToDTO(Organisation organisation) {
        return modelMapper.map(organisation,OrganisationDTO.class);
    }
}
