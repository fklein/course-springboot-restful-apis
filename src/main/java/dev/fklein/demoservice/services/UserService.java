package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
}
