package com.acme.financehub.common;

import static com.acme.financehub.common.Exceptions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiErrorHandler {

  @ExceptionHandler(NotFoundException.class)
  ProblemDetail notFound(NotFoundException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    pd.setTitle("Not Found");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(ForbiddenException.class)
  ProblemDetail forbidden(ForbiddenException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
    pd.setTitle("Forbidden");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(BadRequestException.class)
  ProblemDetail bad(BadRequestException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Bad Request");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ProblemDetail validation(MethodArgumentNotValidException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Validation Error");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(ErrorResponseException.class)
  ProblemDetail generic(ErrorResponseException ex) {
    return ex.getBody();
  }
}
