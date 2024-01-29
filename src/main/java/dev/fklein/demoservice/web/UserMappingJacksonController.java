package dev.fklein.demoservice.web;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import dev.fklein.demoservice.entities.User;
import dev.fklein.demoservice.exceptions.UserNotFoundException;
import dev.fklein.demoservice.services.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

    private final UserService userService;

    @Autowired
    public UserMappingJacksonController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            var user = userService.getUserById(id);
            Set<String> fields = Set.of("id", "ssn", "lastName", "firstName");
            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("customFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);
            mapper.setFilters(filterProvider);
            return mapper;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
