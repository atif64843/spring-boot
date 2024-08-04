package com.cashrich.userprofile_service.repository;

import com.cashrich.userprofile_service.entity.ApiResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiResponseRepository extends JpaRepository<ApiResponseEntity, Long> {
}

