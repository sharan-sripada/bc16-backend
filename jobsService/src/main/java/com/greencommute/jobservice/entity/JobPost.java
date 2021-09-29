package com.greencommute.jobservice.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
)
public class JobPost implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "org_id")
    private UUID orgId;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "experience")
    private String experience;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "is_green_commute")
    boolean isGreenCommute;

    @Type(type = "string-array")
    @Column(name = "job_requirements" , columnDefinition = "text[]")
    private String[] jobRequirements;

    @OneToOne
    @JoinColumn(name = "job_role_id")
    private JobRoles jobRole;

    @Column(name = "address_id")
    private UUID addressId;
}
