package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.OrderService;
import dev.fklein.demoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hateoas/users")
public class OrderHateoasController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public OrderHateoasController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{id}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable("id") Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        Link selfLink = WebMvcLinkBuilder.linkTo(self().getAllOrders(userId)).withSelfRel();
        return CollectionModel.of(user.getOrders(), selfLink);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public EntityModel<Order> getOrderById(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
        Order o = orderService.getOrdersByUserAndId(userId, orderId);
        Link selfLink = WebMvcLinkBuilder.linkTo(self().getOrderById(userId, orderId)).withSelfRel();
        return EntityModel.of(o, selfLink);
    }

    private static OrderHateoasController self() {
        return WebMvcLinkBuilder.methodOn(OrderHateoasController.class);
    }
}
