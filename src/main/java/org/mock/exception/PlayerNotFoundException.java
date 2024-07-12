package org.mock.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String s) {
        super(s);
    }
}
