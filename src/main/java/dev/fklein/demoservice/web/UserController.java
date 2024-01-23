package dev.fklein.demoservice.web;

import dev.fklein.demoservice.services.UserService;
import dev.fklein.demoservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
