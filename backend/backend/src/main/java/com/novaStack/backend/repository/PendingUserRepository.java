package com.novaStack.backend.repository;

import com.novaStack.backend.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {
}
