package com.atipera.github.exception.handler;

import com.atipera.github.exception.GithubRepositoryException;
import com.atipera.github.model.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GithubRepositoryExceptionHandler {

    @ExceptionHandler(GithubRepositoryException.class)
    public ResponseEntity<Response> handleTutorMgmtExceptions(GithubRepositoryException e) {
        Response response = Response.builder().httpStatusCode(e.getHttpStatus().value()).message(e.getMessage()).build();
        return ResponseEntity.status(e.getHttpStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
