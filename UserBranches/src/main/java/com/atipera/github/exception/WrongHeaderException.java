package com.atipera.github.exception;

import com.atipera.github.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class WrongHeaderException extends GithubRepositoryException{
    public WrongHeaderException() {
        super(HttpStatus.NOT_ACCEPTABLE, ErrorMessage.WRONG_HEADER);
    }
}
