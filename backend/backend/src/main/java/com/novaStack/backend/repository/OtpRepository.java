package com.novaStack.backend.repository;

import com.novaStack.backend.model.OtpCode;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpCode, Long> {

    Optional<OtpCode> findFirstByEmailOrderByIdDesc(String email);

    @Modifying
    @Transactional
    @Query("""
            delete from OtpCode
            where expiresAt < :now
            """)
    void deleteByTime(LocalDateTime now);
}
