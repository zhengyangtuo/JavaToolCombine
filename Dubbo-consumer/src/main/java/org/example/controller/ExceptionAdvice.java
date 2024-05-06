package org.example.controller;

import org.example.exception.BussinessException;
import org.example.exception.SystemException;
import org.example.pojo.ResultType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BussinessException.class)
    public ResultType doBusinessException(BussinessException bussinessException)
    {
        return new ResultType(bussinessException.getCode(),bussinessException.getMessage(),null);
    }
    @ExceptionHandler(SystemException.class)
    public ResultType doSystemException(SystemException systemException)
    {
        return new ResultType(systemException.getCode(),systemException.getMessage(),null);
    }
}
