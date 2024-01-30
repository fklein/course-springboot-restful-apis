package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserExistsException;
import dev.fklein.demoservice.exceptions.UserNameNotFoundException;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) throws UserExistsException {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new UserExistsException("A user with the name " + user.getUserName() + " already exists");
        }
        for (Order o : user.getOrders()) {
            o.setUser(user);
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found in repository");
        }
        return user.get();
        //return user.orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found in repository"));
    }

    @Override
    public User updateUserById(Long id, User user) throws UserNotFoundException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException(String.format("User id %d is unknown", id));
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete an unknown user");
        }
    }

    @Override
    public User getUserByUserName(String userName) throws UserNameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UserNameNotFoundException("User named '" + userName + "' is unknown");
        }
        return user;
    }
}
