package com.atipera.github.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Repository {
    private String name;
    private String ownerLogin;
    private List<Branch> branches;
}
