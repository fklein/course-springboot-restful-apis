package dev.fklein.demoservice.web;

import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserExistsException;
import dev.fklein.demoservice.exceptions.UserNameNotFoundException;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.channels.ReadPendingException;
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

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // Without @Valid validation will happen in Hibernate/JPA and fail with an unresolved exception (=> 500/internal server error)
    // @Valid here will result in an MethodArgumentNotValidException that is resolved via DefaultHandlerExceptionResolver (=> 400/bad request)
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, UriComponentsBuilder uriBuilder) {
        try {
            User u = userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setLocation(uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<User>(u, headers, HttpStatus.CREATED);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable(name = "id") Long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable(name = "id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/byName/{username}")
    public User getUserByUserName(@PathVariable(name = "username") String userName) throws UserNameNotFoundException {
        return userService.getUserByUserName(userName);
    }

    /*
    // Will handle any exceptions, e.g. validation failure in Hibernate/JPA
    @ExceptionHandler
    public ResponseEntity<String> handleIt(Exception ex) {
        return ResponseEntity.badRequest().body("It failed big time: " + ex.getMessage());
    }
    */
}
