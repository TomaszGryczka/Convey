package com.github.tomaszgryczka.convey.chat.message;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(String.format(resource));
    }
}
