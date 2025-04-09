package com.example.finalproject.handler;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ExceptionDto> databaseException(DatabaseException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ExceptionDto> reservationException(ReservationException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDto> validationException(ValidationException ex) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var dto = new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Validation Error(s)");
        ex.getBindingResult().getAllErrors().forEach((error) -> dto.addDetail(error.getDefaultMessage()));
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
