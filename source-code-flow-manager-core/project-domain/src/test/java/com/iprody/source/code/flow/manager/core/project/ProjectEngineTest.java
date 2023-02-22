package com.iprody.source.code.flow.manager.core.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test, for checking of operations on ProjectEngine.
 */
@ExtendWith(MockitoExtension.class)
class ProjectEngineTest {

    /**
     * Mock representation of Project request mapper.
     */
    @Mock
    private GitProjectRequestMapper gitProjectRequestMapper;

    /**
     * Mock representation of Project response mapper.
     */
    @Mock
    private GitProjectResponseMapper gitProjectResponseMapper;

    /**
     * Mock representation of abstract Output port.
     */
    @Mock
    private GitProjectOutputPort gitProjectOutputPort;

    /**
     * Creates a Project, using createObject method,
     * should return a valid NotNull response.
     */
    @Test
    void projectEngineWhenCrateProject_WillReturnNotNull() {
        when(gitProjectRequestMapper.map(any())).thenReturn(Mono.just(new GitProject()));
        when(gitProjectOutputPort.createProject(any())).thenReturn(Mono.just(new GitProject()));

        final var gitResponse = new GitProjectResponseTestImplementation();
        when(gitProjectResponseMapper.map(any())).thenReturn(Mono.just(gitResponse));

        final var engine = new ProjectEngine(gitProjectRequestMapper, gitProjectResponseMapper, gitProjectOutputPort);
        engine.createProject(Mono.just(new GitProjectRequestTestImplementation()));

        StepVerifier
                .create(engine.createProject(Mono.just(new GitProjectRequestTestImplementation())))
                .expectNext(gitResponse)
                .verifyComplete();
    }

    /**
     * Specific test implementation of a Project request object.
     */
    static class GitProjectRequestTestImplementation implements GitProjectRequest {
    }

    /**
     * Specific test implementation of a Project response object.
     */
    static class GitProjectResponseTestImplementation implements GitProjectResponse {
    }
}

