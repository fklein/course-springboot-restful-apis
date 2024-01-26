package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.Order;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Validated
@RequestMapping("/hateoas/users")
public class UserHateoasController {

    private final UserService userService;

    @Autowired
    public UserHateoasController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public CollectionModel<User> getAllUsers() throws UserNotFoundException {
        var users = userService.getAllUsers();
        for (var user : users) {
            Long userId = user.getId();
            Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selflink);
            /*
            Link orderlink = WebMvcLinkBuilder
                    .linkTo(OrderHateoasController.class)
                    .slash(userId)
                    .slash("orders")
                    .withRel("all-orders");;
            */
            var orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
            Link orderLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
            user.add(orderLink);
        }
        Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();

        return CollectionModel.of(users, selflink);
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            User user = userService.getUserById(id);
            Long userId = user.getId();
            Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selflink);

            var orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
            Link orderLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
            user.add(orderLink);
            
            return EntityModel.of(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
