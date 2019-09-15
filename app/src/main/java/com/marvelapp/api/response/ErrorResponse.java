package com.marvelapp.api.response;

/**
 * Model class for representing the error results
 */
public class ErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(){
        // Empty constructor
    }
}
