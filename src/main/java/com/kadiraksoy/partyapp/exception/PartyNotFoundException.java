package com.kadiraksoy.partyapp.exception;

public class PartyNotFoundException extends RuntimeException{

    public PartyNotFoundException(String message) {
        super(message);
    }
}
