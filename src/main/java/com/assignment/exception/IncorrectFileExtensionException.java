package com.assignment.exception;

public class IncorrectFileExtensionException extends Exception {
    public IncorrectFileExtensionException(String errorMessage) {
        super(errorMessage);
    }
}