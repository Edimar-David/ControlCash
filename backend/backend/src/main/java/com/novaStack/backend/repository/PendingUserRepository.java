package com.novaStack.backend.repository;

import com.novaStack.backend.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {
    PendingUser findByEmail(String email);
}
