package com.greencommute.organizationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="organisations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name="logo_url")
    private String logoUrl;

    @Column(name="description")
    private String description;

    @Column(name="website_url")
    private String websiteUrl;

    @Column(name="member_since")
    private String memberSince;

    @OneToMany(mappedBy = "organisations")
    private List<Address> addresses;

}