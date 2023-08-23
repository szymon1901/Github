package com.atipera.github.controller;

import com.atipera.github.exception.WrongHeaderException;
import com.atipera.github.model.Repository;
import com.atipera.github.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/repositories")
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping(value = "/{username}")
    public ResponseEntity<List<Repository>> getRepositories(@RequestHeader(value = "Accept") String acceptHeader, @PathVariable String username) {
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)){
            throw new WrongHeaderException();
        }
        List<Repository> userRepositories = githubService.getUserRepositories(username);
        System.out.println(userRepositories);
        return ResponseEntity.ok(githubService.getUserRepositories(username));
    }
}
