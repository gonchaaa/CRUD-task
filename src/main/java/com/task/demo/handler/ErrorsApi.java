package com.task.demo.handler;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorsApi<E> {
private Integer code;
private Exception<E> exception;

}
