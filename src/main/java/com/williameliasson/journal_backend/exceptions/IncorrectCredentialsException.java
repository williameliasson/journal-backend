package com.williameliasson.journal_backend.exceptions;

public class IncorrectCredentialsException extends RuntimeException{
    public IncorrectCredentialsException(String string){
        super(string);
    }
}
