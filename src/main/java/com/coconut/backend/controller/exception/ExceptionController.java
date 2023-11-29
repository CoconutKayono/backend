package com.coconut.backend.controller.exception;

import com.coconut.backend.entity.RestBean;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ValidationException.class)
    public RestBean<Void> validateException(ValidationException exception){
        log.warn(("Resolve [{}: {}]"),exception.getClass().getName(),exception.getMessage());
        return RestBean.failure(400,"请求参数有误");
    }
}
