package dev.fklein.demoservice.exceptions;

public class UserExistsException extends Exception {

    public UserExistsException(String message) {
        super(message);
    }
}
