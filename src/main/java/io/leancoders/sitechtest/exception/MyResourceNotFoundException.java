package io.leancoders.sitechtest.exception;

public class MyResourceNotFoundException extends RuntimeException {

    public MyResourceNotFoundException(String message) {
        super(message);
    }
}
