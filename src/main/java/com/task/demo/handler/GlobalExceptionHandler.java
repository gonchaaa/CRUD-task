package com.task.demo.handler;


import com.task.demo.exceptions.BaseException;
import com.task.demo.exceptions.ErrorMessage;
import com.task.demo.exceptions.ErrorsType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ErrorsApi> handleBaseException(BaseException baseException, WebRequest request){
            return ResponseEntity.badRequest().body(createErrorsApi(baseException.getMessage(),request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsApi> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList()).toString();

        return ResponseEntity.badRequest().body(createErrorsApi(errorMessage, request));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorsApi> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        String errorMessage = ErrorsType.UNIQUE_CONSTRAINT.getMessage();
        return ResponseEntity.badRequest().body(createErrorsApi(errorMessage, request));
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
