package com.iprody.source.code.flow.manager.gitlab.api.project.mappers;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.core.project.GitProjectRequestMapper;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectRequest;
import reactor.core.publisher.Mono;

/**
 * Mapper of the data from a Mono<GitlabProjectRequest> located in Gitlab module to a Mono<GitProject> from Core module.
 */
public class GitlabProjectRequestMapper implements GitProjectRequestMapper<GitlabProjectRequest> {

    /**
     * Method that takes a Mono<GitlabProjectRequest> and returns a draft of a Mono<GitProject>.
     */
    @Override
    public Mono<GitProject> map(Mono<GitlabProjectRequest> request) {
        return request.map(this::setProjectRequestData);
    }

    /**
     * Maps data from a GitlabProjectRequest object to a GitProject object.
     *
     * @param projectRequest object that contains the data that the user has entered in the form.
     * @return A GitProject object.
     */
    private GitProject setProjectRequestData(GitlabProjectRequest projectRequest) {
        final var project = new GitProject();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setNamespace(projectRequest.getNamespace());

        return project;
    }
}
