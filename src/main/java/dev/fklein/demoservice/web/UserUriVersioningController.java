package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDto;
import dev.fklein.demoservice.dto.UserDtoV1;
import dev.fklein.demoservice.dto.UserDtoV2;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import jakarta.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/versioning/uri/users")
public class UserUriVersioningController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserUriVersioningController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"/v1.0/{id}", "/v1.1/{id}"})
    public UserDtoV1 getUserByIdV1(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserDtoV1 userDto = modelMapper.map(user, UserDtoV1.class);
            return userDto;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/v2.0/{id}")
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
