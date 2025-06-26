package com.waltmann.waltmann_api.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String email) {
        super(String.format("User with the email address '%s' already exists.", email));
    }
}
