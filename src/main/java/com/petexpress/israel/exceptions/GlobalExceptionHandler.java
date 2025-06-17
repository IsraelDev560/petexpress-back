package com.petexpress.israel.exceptions;


import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @ExceptionHandler(org.springframework.security.core.userdetails.UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> usernameNotFoundException(org.springframework.security.core.userdetails.UsernameNotFoundException ex) {
        var error = new ErrorResponseDto(401, "Invalid username or password.", timestamp());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UserExceptions.ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> userAlreadyExistsException(UserExceptions.ResourceAlreadyExistsException ex) {
        var error = new ErrorResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage(), timestamp());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserExceptions.UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> unauthorizedException(UserExceptions.UnauthorizedException ex) {
        var error = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), timestamp());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({
            org.springframework.security.authentication.BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponseDto> authenticationException(Exception ex) {
        logger.error("Authentication error", ex);
        var error = new ErrorResponseDto(401, "Invalid username or password.", timestamp());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ErrorResponseDto> jwtVerificationException(JWTVerificationException ex) {
        var error = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired token.", timestamp());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> genericException(Exception ex) {
        var error = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error.", timestamp());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
