package dev.fklein.demoservice.web;

import dev.fklein.demoservice.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public HelloWorldController(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // @GetMapping("/helloworld")
    @RequestMapping(method= RequestMethod.GET, path="/helloworld")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/userdetails")
    public UserDetails getUserDetails() {
        return new UserDetails("Max", "Mustermann", "München");
    }

    @GetMapping("/helloworld-int")
    public String getMessagesInI18NFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        Locale l;
        try {
            l = new Locale(locale);
        } catch (Exception e) {
            l = Locale.US;
        }
        return messageSource.getMessage("label.hello", null, l);
    }

    @GetMapping("/helloworld-int2")
    public String getMessagesInI18NFormat2() {
        return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
    }
}
