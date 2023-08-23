package com.atipera.github.exception;

import com.atipera.github.enums.ErrorMessage;
import org.springframework.http.HttpStatus;

public class GithubResponseException extends GithubRepositoryException{
    public GithubResponseException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.API_RESPONSE_FAIL);
    }
}
