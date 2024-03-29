package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserExistsException;
import dev.fklein.demoservice.exceptions.UserNameNotFoundException;
import dev.fklein.demoservice.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user) throws UserExistsException;
    User getUserById(Long id) throws UserNotFoundException;
    User updateUserById(Long id, User user) throws UserNotFoundException;
    void deleteUserById(Long id);
    User getUserByUserName(String userName) throws UserNameNotFoundException;
}
