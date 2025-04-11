package com.task.demo.handler;

import lombok.Data;

import java.util.Date;


@Data
public class Exception<E> {
    private String hostName;
    private String path;
    private Date createdDate;
    private E message;
}
