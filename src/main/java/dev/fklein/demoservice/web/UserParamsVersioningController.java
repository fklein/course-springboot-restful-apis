package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDtoV1;
import dev.fklein.demoservice.dto.UserDtoV2;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/versioning/param/users")
public class UserParamsVersioningController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserParamsVersioningController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // http://localhost:8080/versioning/param/users/101?version=1
    @GetMapping(value = "/{id}", params = "version=1")
    public UserDtoV1 getUserByIdV1(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserDtoV1 userDto = modelMapper.map(user, UserDtoV1.class);
            return userDto;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // http://localhost:8080/versioning/param/users/101?version=2
    @GetMapping(value = "/{id}", params = "version=2")
    public UserDtoV2 getUserByIdV2(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserDtoV2 userDto = modelMapper.map(user, UserDtoV2.class);
            return userDto;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
