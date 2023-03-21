package com.iprody.source.code.flow.manager.gitlab.api.member;

import com.iprody.source.code.flow.manager.core.member.GitMember;
import com.iprody.source.code.flow.manager.core.member.GitMemberOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Instant;

/**
 * It's a class that implements the GitMemberOutputPort interface.
 */
@Component
@RequiredArgsConstructor
public class MemberOutputPort implements GitMemberOutputPort {

    /**
     * Injected MemberApi.
     */
    private final MemberApi memberApi;

    /**
     * Add a member to a project.
     *
     * @param userId The id of the user to add to the project
     * @param projectId The id of the project you want to add the member to.
     * @param accessLevel Level of access.
     * @return A Mono<GitMember>
     */
    @Override
    public Mono<GitMember> addMember(long userId, long projectId, int accessLevel) {
        return memberApi.addProjectMember(userId, projectId, AccessLevel.valueOf(accessLevel))
                .map(this::map);
    }

    /**
     * It takes a Member object and returns a GitMember object.
     *
     * @param member The member object that we want to map to a GitMember object.
     * @return A GitMember object
     */
    private GitMember map(Member member) {
        final GitMember gitMember = new GitMember();
        gitMember.setId(member.getId());
        gitMember.setUsername(member.getUsername());
        gitMember.setName(member.getName());
        gitMember.setState(member.getState());
        gitMember.setWebUrl(URI.create(member.getWebUrl()));
        gitMember.setCreatedAt(Instant.parse(member.getCreatedAt()));
        gitMember.setExpiresAt(Instant.parse(member.getExpiresAt()));
        gitMember.setAccessLevel(member.getAccessLevel());
        gitMember.setEmail(member.getEmail());
        return gitMember;
    }
}
