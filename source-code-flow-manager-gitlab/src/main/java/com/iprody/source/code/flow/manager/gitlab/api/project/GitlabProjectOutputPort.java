package com.iprody.source.code.flow.manager.gitlab.api.project;

import com.iprody.source.code.flow.manager.core.project.GitProject;
import com.iprody.source.code.flow.manager.core.project.GitProjectOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class GitlabProjectOutputPort implements GitProjectOutputPort {

    /**
     * Configured and injected ProjectApi.
     */
    private final ProjectApi projectApi;

    /**
     *
     * @param gitProject consists of configured data to create a new project in a remote repository
     * @return
     */
    @Override
    public Mono<GitProject> createProject(Mono<GitProject> gitProject) {
        return gitProject.map(this::prepareProjectCreationRequest)
                .flatMap(projectApi::createProject)
                .map(this::map);
    }

    /**
     * This method obtains GitProject and converts it to ProjectCreationRequest,
     * which need for further work.
     * @param gitProject Object that we want to convert.
     * @return ProjectCreationRequest
     */
    private ProjectCreationRequest prepareProjectCreationRequest(GitProject gitProject) {
        final ProjectCreationRequest pcr = new ProjectCreationRequest();
        pcr.setName(gitProject.getName());
        pcr.setDefaultBranch(gitProject.getBaseBranch());
        pcr.setLfsEnabled(gitProject.isLfsEnabled());
        pcr.setWikiEnabled(gitProject.isWikiEnabled());
        pcr.setVisibility(gitProject.isVisible() ? "public" : "private");
        pcr.setNamespaceId((int) gitProject.getCreatorID());
        pcr.setIssuesEnabled(gitProject.isIssuesEnabled());
        return pcr;
    }

    /**
     * This method obtains Project and convert it to GitProject.
     * @param project Object that we would like to map to GitProject.
     * @return a GitProject
     */
    public GitProject map(Project project) {
        final GitProject gitProject = new GitProject();
        gitProject.setId(project.getId());
        gitProject.setName(project.getName());
        gitProject.setCreatedAT(Instant.parse(project.getCreatedAt()));
        gitProject.setReadmeUrl(URI.create(project.getReadmeUrl()));
        gitProject.setHttpUrlToRepo(URI.create(project.getHttpUrlToRepo()));
        gitProject.setNamespace(project.getNameWithNamespace());
        gitProject.setDescription(project.getDescription());
        return gitProject;
    }
}
