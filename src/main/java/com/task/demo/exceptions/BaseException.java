package com.task.demo.exceptions;

public class BaseException extends RuntimeException {
    public BaseException() {

    }
    public BaseException(ErrorMessage errorMessage) {
        super(errorMessage.errorMessage());
    }
}
