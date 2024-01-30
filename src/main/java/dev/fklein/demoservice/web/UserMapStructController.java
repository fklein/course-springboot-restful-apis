package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDto;
import dev.fklein.demoservice.dto.UserMsDto;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.mappers.UserMapper;
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
@RequestMapping("/mapstruct/users")
public class UserMapStructController {

    private final UserService userService;

    private final UserMapper userMapper;

    @Autowired
    public UserMapStructController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public UserMsDto getMapStructUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserMsDto userDto = userMapper.userToUserDto(user);
            return userDto;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
