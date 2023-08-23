package com.atipera.github.service;

import com.atipera.github.exception.GithubResponseException;
import com.atipera.github.exception.UserNotFoundException;
import com.atipera.github.model.Branch;
import com.atipera.github.model.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    private static final String GITHUB_API_URL = "https://api.github.com/";

    public List<Repository> getUserRepositories(String username) {
        String apiUrl = GITHUB_API_URL + "users/" + username + "/repos";

        HttpResponse<String> apiResponse = apiResponse(apiUrl);
        return processResponse(apiResponse, username);
    }

    private HttpResponse<String> apiResponse(String apiUrl) {
        HttpRequest request = buildRequest(apiUrl);
        return sendRequest(request);
    }

    private HttpRequest buildRequest(String apiUrl) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .headers("accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Repository> processResponse(HttpResponse<String> response, String username) {
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            JSONArray repoArray = new JSONArray(response.body());
            return createRepositoryList(repoArray, username);
        } else if (statusCode == 404) {
            throw new UserNotFoundException();
        }
        throw new GithubResponseException();
    }
    private List<Repository> createRepositoryList(JSONArray repoArray, String username) {
        List<Repository> repositories = new ArrayList<>();
        for (Object repo : repoArray) {
            JSONObject repoJson = (JSONObject) repo;
            boolean containsFork = repoJson.getBoolean("fork");
            if (!containsFork) {
                Repository repository = createRepository(repoJson, username);
                repositories.add(repository);
            }
        }
        return repositories;
    }

    private Repository createRepository(JSONObject repoJson, String username) {
        String repoName = repoJson.getString("name");
        String ownerLogin = repoJson.getJSONObject("owner").getString("login");
        List<Branch> branchList = getBranchList(username, repoName);
        return Repository.builder()
                .name(repoName)
                .ownerLogin(ownerLogin)
                .branches(branchList)
                .build();
    }

    private List<Branch> getBranchList(String username, String repoName) {
        JSONArray branchesArray = getBranchesWithCommits(username, repoName);
        return createBranchList(branchesArray);
    }

    private JSONArray getBranchesWithCommits(String ownerLogin, String repositoryName) {
        String apiUrl = GITHUB_API_URL + "repos/" + ownerLogin + "/" + repositoryName + "/branches";

        HttpResponse<String> response = apiResponse(apiUrl);
        boolean isCorrectStatusCode = response.statusCode() == 200;
        if (!isCorrectStatusCode) {
            throw new GithubResponseException();
        }
        return new JSONArray(response.body());
    }

    private List<Branch> createBranchList(JSONArray branchesArray) {
        List<Branch> branches = new ArrayList<>();
        for (Object branchObj : branchesArray) {
            JSONObject branchJson = (JSONObject) branchObj;
            String branchName = branchJson.getString("name");
            String lastCommitSha = branchJson.getJSONObject("commit").getString("sha");
            Branch branch = Branch.builder()
                    .name(branchName)
                    .lastCommitSha(lastCommitSha)
                    .build();
            branches.add(branch);
        }
        return branches;
    }
}

