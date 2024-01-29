package dev.fklein.demoservice.web;

import com.fasterxml.jackson.annotation.JsonView;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.entities.Views;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/jsonview/users")
// Without this, @Min will cause an "HandlerMethodValidationException" instead of "ConstraintViolationException"
@Validated
public class UserJsonViewController {

    private final UserService userService;

    @Autowired
    public UserJsonViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/external/{id}")
    @JsonView(Views.External.class)
    public User getUserByIdExternal(@PathVariable("id") @Min(1) Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @JsonView(Views.Internal.class)
    @GetMapping("/internal/{id}")
    public User getUserByIdInternal(@PathVariable("id") @Min(1) Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
