package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping     // Inherit mapping URL from class
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable(name = "id") Long  id) {
        return userService.getUserById(id);

    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable(name = "id") Long  id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable(name = "id") Long  id) {
        userService.deleteUserById(id);
    }
}
