package dev.fklein.demoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class DemoRestfulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRestfulServiceApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver locale = new AcceptHeaderLocaleResolver();
        locale.setDefaultLocale(Locale.US);
        return locale;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("internationalization/messages");
        return messageSource;
    }
}
