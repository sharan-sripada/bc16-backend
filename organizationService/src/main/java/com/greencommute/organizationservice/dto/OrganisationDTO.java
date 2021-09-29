package com.greencommute.organizationservice.dto;

import java.util.List;
import java.util.UUID;
import com.greencommute.organizationservice.entity.Address;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDTO {

    private UUID id;
    private String name;
    private String logoUrl;
    private String description;
    private String websiteUrl;
    private String memberSince;
    private List<Address> addresses;

}
