package com.task.demo.exceptions;


import lombok.Getter;

@Getter
public enum ErrorsType {

    NO_DATA_FOUND("1001","data yoxdur"),
    NO_BALANCE("1002","Kifayet qeder balans yoxdur"),
    INVALID_OPERATION("1003","Kart aktiv deyil"),
    UNIQUE_CONSTRAINT("1004","Iban hesabi artiq yaradilib");

    private  String code;
    private  String message;

    ErrorsType(String code, String message) {
        this.code=code;
        this.message=message;
    }

}
