package com.cashrich.userprofile_service.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse {
        private Long userId;
        private String response;
        private LocalDateTime timestamp;

}
