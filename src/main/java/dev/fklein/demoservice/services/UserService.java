package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    User getUserById(Long id) throws UserNotFoundException;
    User updateUserById(Long id, User user) throws UserNotFoundException;
    void deleteUserById(Long id);
    User getUserByUserName(String userName);
}
