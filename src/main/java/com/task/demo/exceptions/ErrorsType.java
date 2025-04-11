package com.task.demo.exceptions;


import lombok.Getter;

@Getter
public enum ErrorsType {

    NO_DATA_FOUND("1001","data yoxdur");

    private  String code;
    private  String message;

    ErrorsType(String code, String message) {
        this.code=code;
        this.message=message;
    }

}
