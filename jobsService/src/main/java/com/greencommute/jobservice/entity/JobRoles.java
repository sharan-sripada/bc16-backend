package com.greencommute.jobservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "job_roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobRoles implements Serializable {

    @Id
    @GeneratedValue
    @Column(length = 36, nullable = false, updatable = false)
    UUID id;

    @Column(name = "name")
    String name;

}
