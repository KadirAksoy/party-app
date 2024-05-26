package com.kadiraksoy.partyapp.exception;

public class UserNotAdminException extends RuntimeException{
    public UserNotAdminException(String message) {
        super(message);
    }
}
