package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // @GetMapping("/helloworld")
    @RequestMapping(method= RequestMethod.GET, path="/helloworld")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/userdetails")
    public UserDetails getUserDetails() {
        return new UserDetails("Max", "Mustermann", "München");
    }
}
