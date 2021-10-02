package com.cifer.categories.services.users;

import com.cifer.categories.entities.User;

import java.util.Optional;

/**
 * User service. All operations under user entity must be implement through this service.
 */
public interface UsersService {
    void createOrUpdate(UserData userData);
    Optional<User> getBySessionId(String sessionId);
}
