package com.cognizant.TaskManagementSystem.exceptions;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException(String message) {
        super(message);
    }
}
