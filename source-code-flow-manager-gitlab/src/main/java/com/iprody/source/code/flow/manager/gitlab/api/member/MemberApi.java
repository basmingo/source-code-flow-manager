package com.iprody.source.code.flow.manager.gitlab.api.member;

import com.iprody.source.code.flow.manager.gitlab.exception.GitlabApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * The class represents a client side Member API for interacting with the vendor's API.
 */
@Component
@RequiredArgsConstructor
public class MemberApi {

    /**
     * Configured and injected WebClient.
     */
    private final WebClient webClient;

    /**
     * > Add a member to a project.
     *
     * @param userId      The ID of the user to add to the project.
     * @param projectId   The ID of the project to add the member to.
     * @param accessLevel The access level of the member.
     * @return A Mono<Member>
     */
    public Mono<Member> addProjectMember(final long userId,
                                         final long projectId,
                                         AccessLevel accessLevel) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/projects/{id}/members")
                        .queryParam("user_id", userId)
                        .queryParam("access_level", accessLevel.getLevelValue())
                        .build(projectId)
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Member.class)
                .onErrorMap(throwable -> new GitlabApiException(
                        "Something went wrong during Gitlab operation [add member] execution.",
                        throwable));
    }
}
