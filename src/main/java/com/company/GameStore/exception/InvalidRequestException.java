package com.company.GameStore.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) { super(message); }
    public InvalidRequestException() { super(); }
}
