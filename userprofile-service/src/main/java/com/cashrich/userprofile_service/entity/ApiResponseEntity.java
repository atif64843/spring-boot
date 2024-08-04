package com.cashrich.userprofile_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "api_responses")
public class ApiResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}