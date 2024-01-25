package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class OrderController {

    private final UserService userService;

    @Autowired
    public OrderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return user.getOrders();
    }
}
