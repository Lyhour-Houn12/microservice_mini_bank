package com.jing.accounts.exception;


import com.jing.accounts.payload.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validateErrors = ex.getBindingResult().getAllErrors();
        validateErrors.forEach(error -> {
                String fieldName = ((FieldError)  error).getField();
                String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> validationErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setApiPath(request.getRequestURI());
        errorResponseDto.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);

        Throwable rootCause = ex;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        errorResponseDto.setErrorMessage(rootCause.getMessage() != null ? rootCause.getMessage() : ex.getMessage());

        errorResponseDto.setErrorTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setApiPath(request.getRequestURI());
        errorResponseDto.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponseDto.setErrorMessage(ex.getMessage());
        errorResponseDto.setErrorTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setApiPath(request.getRequestURI());
        errorResponseDto.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponseDto.setErrorMessage(ex.getMessage());
        errorResponseDto.setErrorTime(LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }






}
