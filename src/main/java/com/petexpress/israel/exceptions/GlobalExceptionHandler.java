package com.petexpress.israel.exceptions;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm:ss"));

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerException(Exception exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(), "Ocorreu um erro inesperado.", time);
        return ResponseEntity.status(errorResponseDto.status()).body(errorResponseDto);
    }

    @ExceptionHandler(UserExceptions.ResourceNotFoundException.class)
    private ResponseEntity<ErrorResponseDto> userNotFoundException(UserExceptions.ResourceNotFoundException exception){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), "Esse usuário não existe.", time);
        return ResponseEntity.status(errorResponseDto.status()).body(errorResponseDto);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponseDto> unauthorizedUser(JWTVerificationException exception){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Unauthorized", time);
        return ResponseEntity.status(errorResponseDto.status()).body(errorResponseDto);
    }
}
