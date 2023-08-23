package com.atipera.github.exception;

import com.atipera.github.enums.ErrorMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GithubRepositoryException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GithubRepositoryException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getText());
        this.httpStatus = httpStatus;
    }
}
