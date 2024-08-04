package com.cashrich.userprofile_service.repository;

import com.cashrich.userprofile_service.entity.UserEntity;
import com.cashrich.userprofile_service.model.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

}

