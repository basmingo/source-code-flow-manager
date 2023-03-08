package com.iprody.source.code.flow.manager.gitlab.api.project.controller;

import com.iprody.source.code.flow.manager.core.project.ProjectEngine;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectRequest;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * It is a controller to work with Gitlab projects.
 * <p>
 * It listens endpoint "/gitlab/projects"
 */
@RestController
@RequestMapping("/gitlab/projects")
@RequiredArgsConstructor
public class GitlabController {

    /**
     * Injection of ProjectEngine bean from Core module.
     * ProjectEngine is a service which creates project for a specific vendor.
     */
    private final ProjectEngine<GitlabProjectRequest, GitlabProjectResponse> projectEngine;

    /**
     * Post request to create a new project in Gitlab.
     *
     * @param request for project creation which is come from the request
     * @return GitlabProjectResponse
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created project"),
            @ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
            @ApiResponse(responseCode = "500", description = "Internal error")})
    public Mono<GitlabProjectResponse> createProject(@Valid @RequestBody GitlabProjectRequest request) {
        return projectEngine.createProject(request);
    }
}

