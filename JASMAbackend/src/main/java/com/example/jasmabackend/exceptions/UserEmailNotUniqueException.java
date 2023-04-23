package com.example.jasmabackend.exceptions;

public class UserEmailNotUniqueException extends Exception {

    public UserEmailNotUniqueException(String email) {
        super("User email not unique: " + email);
    }
}
