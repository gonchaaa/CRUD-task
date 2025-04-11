package com.task.demo.exceptions;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private List<String> addMapValue(List<String> list, String value){
        list.add(value);
        return list;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsApi<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errosMap = new HashMap<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError)error).getField();
            if(errosMap.containsKey(fieldName)) {
                errosMap.put(fieldName,addMapValue(errosMap.get(fieldName),error.getDefaultMessage()));
            }
            errosMap.put(fieldName,addMapValue(new ArrayList<>(),error.getDefaultMessage()));

        }
        return ResponseEntity.badRequest().body(createErrorsApi(errosMap));

    }

    private <T> ErrorsApi<T> createErrorsApi(T errosMap) {
        ErrorsApi<T> errorsApi = new ErrorsApi<T>();
        errorsApi.setErrorMessage(errosMap);
        errorsApi.setErrorTime(new Date());
        errorsApi.setId(UUID.randomUUID().toString());
        return errorsApi;
    }
}
