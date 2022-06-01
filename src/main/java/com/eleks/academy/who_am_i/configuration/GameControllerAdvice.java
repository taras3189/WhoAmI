package com.eleks.academy.who_am_i.configuration;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

import com.eleks.academy.who_am_i.core.exception.ErrorResponse;
import com.eleks.academy.who_am_i.core.exception.GameException;
import com.eleks.academy.who_am_i.model.response.ApiError;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GameControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleGameException(GameException gameException) {
        return gameException::getMessage;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(collectingAndThen(toList(), details ->
                        ResponseEntity.badRequest().body(new ErrorResponse("Validation failed!", details))));
    }

}
