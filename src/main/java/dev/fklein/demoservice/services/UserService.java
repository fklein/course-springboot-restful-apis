package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> getUserById(Long id);
}
