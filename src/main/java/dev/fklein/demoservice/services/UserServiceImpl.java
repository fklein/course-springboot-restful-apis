package dev.fklein.demoservice.services;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);//.orElse(null);
    }

    @Override
    public User updateUserById(Long id, User user) throws Exception {
        if (userRepository.findById(id).isEmpty()) {
            throw new Exception(String.format("User id %d is unknown", id));
        }
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }
}
