package com.greencommute.jobservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

        private int status;
        private String message;
        private long timeStamp;
}
