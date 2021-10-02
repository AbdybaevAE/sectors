package com.cifer.categories.repos;

import com.cifer.categories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for users
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserBySessionId(String sessionId);
}
