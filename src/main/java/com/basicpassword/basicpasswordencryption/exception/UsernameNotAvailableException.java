package com.basicpassword.basicpasswordencryption.exception;

public class UsernameNotAvailableException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UsernameNotAvailableException() {
        super("Username is not available.");
    }
}
