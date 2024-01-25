package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserExistsException;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.OrderService;
import dev.fklein.demoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return user.getOrders();
    }

    @PostMapping("/{id}/orders/alternative")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void createOrderAlternative(@PathVariable("id") Long userId, @RequestBody Order order) throws UserNotFoundException {
        // To insert an update via the user, cascading is required, see User.java
        User u = userService.getUserById(userId);
        order.setUser(u);
        u.getOrders().add(order);
        userService.updateUserById(userId, u);
    }

    @PostMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@PathVariable("id") Long userId, @RequestBody Order order) throws UserNotFoundException, UserExistsException {
        User u = userService.getUserById(userId);
        // This will also work: User u = new User(); u.setId(userId);
        order.setUser(u);
        orderService.createOrder(order);
    }
}
