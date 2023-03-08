package com.iprody.source.code.flow.manager.gitlab.api.project.mappers;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.core.project.GitProjectResponseMapper;
import com.iprody.source.code.flow.manager.gitlab.api.project.dto.GitlabProjectResponse;
import reactor.core.publisher.Mono;

/**
 * Mapper of the data from a Mono<GitProject> from Core module to Mono<GitlabProjectResponse> located in Gitlab module.
 */
public class GitlabProjectResponseMapper implements GitProjectResponseMapper<GitlabProjectResponse> {

    /**
     * Method that takes a fully configured Mono<GitProject> and returns a Mono<GitlabProjectResponse>.
     */
    @Override
    public Mono<GitlabProjectResponse> map(Mono<GitProject> gitProject) {
        return gitProject.map(GitlabProjectResponseMapper::setDataFromProjectToResponse);
    }

    /**
     * It maps GitProject object data to a GitlabProjectResponse object.
     *
     * @param project object that is being converted to a response object.
     * @return GitlabProjectResponse object
     */
    private static GitlabProjectResponse setDataFromProjectToResponse(GitProject project) {
        final var response = new GitlabProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setNamespace(project.getNamespace());
        response.setCreatedAt(project.getCreatedAT());
        response.setLfsEnabled(project.isLfsEnabled());
        response.setWikiEnabled(project.isWikiEnabled());
        response.setCiCdEnabled(project.isCiCdEnabled());
        response.setContainerRegistryEnabled(project.isContainerRegistryEnabled());
        response.setArtifactRegistryEnabled(project.isArtifactRegistryEnabled());
        response.setHttpUrlToRepo(project.getHttpUrlToRepo());
        response.setReadmeUrl(project.getReadmeUrl());

        return response;
    }
}
