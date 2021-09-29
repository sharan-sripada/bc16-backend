package com.greencommute.jobservice.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationPOJO  implements Serializable {
    private UUID id;
    private String name;
    private String logoUrl;
    private String description;
    private String websiteUrl;
    private String memberSince;
    private AddressPOJO[] addresses;
}
