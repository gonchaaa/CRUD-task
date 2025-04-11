package com.task.demo.exceptions;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorsApi<T> {
    private String id;
    private Date errorTime;
    private T errorMessage;
}
