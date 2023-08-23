package com.atipera.github.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Response {
    private int httpStatusCode;
    private String message;
}
