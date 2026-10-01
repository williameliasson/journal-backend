package com.williameliasson.journal_backend.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String string){
        super(string);
    }    
}
