package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDto;
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
@RequestMapping("/modelmapper/users")
@Validated
public class UserModelMapperController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserModelMapperController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public UserDto getModelMapperUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return userDto;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
