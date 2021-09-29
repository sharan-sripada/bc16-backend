package com.greencommute.jobservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobPostDTO {

    private UUID id;
    private String jobTitle;
    private UUID orgId;
    private String description;
    private String experience;
    private String jobType;
    boolean isGreenCommute;
    private String[] jobRequirements;
    private UUID addressId;
    private JobRolesDTO jobRole;
    private OrganizationPOJO organization;
    boolean isSaved;
}
