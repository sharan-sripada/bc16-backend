package com.greencommute.jobservice.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveJobsDTO {

    private UUID id;
    private JobPostDTO jobPost;
    private UUID userId;
}
