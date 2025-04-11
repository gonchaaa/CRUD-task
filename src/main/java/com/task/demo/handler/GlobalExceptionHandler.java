package com.task.demo.handler;


import com.task.demo.exceptions.BaseException;
import com.task.demo.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ErrorsApi> handleBaseException(BaseException baseException, WebRequest request){
            return ResponseEntity.badRequest().body(createErrorsApi(baseException.getMessage(),request));
    }

    private String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public <E> ErrorsApi<E> createErrorsApi(E message,WebRequest webRequest){
        ErrorsApi<E> errorsApi = new ErrorsApi<>();
        errorsApi.setCode(HttpStatus.BAD_REQUEST.value());

        Exception<E> exception = new Exception<>();
        exception.setCreatedDate(new Date());
        exception.setHostName(getHostName());
        exception.setPath(webRequest.getDescription(false));
        exception.setMessage(message);


        errorsApi.setException(exception);
        return errorsApi;
    }
}
