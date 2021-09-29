package com.greencommute.jobservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "saved_jobs")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveJobs {

    @Id
    @GeneratedValue
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private JobPost jobPost;

    @Column(name = "user_id")
    private UUID userId;
}
