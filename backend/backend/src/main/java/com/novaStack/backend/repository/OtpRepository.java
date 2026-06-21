package com.novaStack.backend.repository;

import com.novaStack.backend.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<OtpCode, Long> {
}
