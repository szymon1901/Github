package com.atipera.github.exception;

import com.atipera.github.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GithubRepositoryException{
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND);
    }
}
