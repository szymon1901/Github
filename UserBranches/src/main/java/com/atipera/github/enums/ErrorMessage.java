package com.atipera.github.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    API_RESPONSE_FAIL("There was a problem returning a response from the server"),
    USER_NOT_FOUND("The user with the specified name was not found"),
    WRONG_HEADER("The header given is not acceptable");
    private final String text;

    ErrorMessage(String text) {
        this.text = text;
    }
}
