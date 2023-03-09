package com.iprody.source.code.flow.manager.core.member;

import reactor.core.publisher.Mono;

/**
 * Interface represents a set of operations for manipulating over a member
 * that established remotely on git-vendor (aka Gitlab, Github, etc.) premises.
 */
public interface GitMemberOutputPort {

    /**
     * Adds a member to a project.
     *
     * @param userId      The userId of the user to add to the project.
     * @param projectId   The ID of the project.
     * @param accessLevel Level of access.
     * @return A Mono<GitMember>
     */
    Mono<GitMember> addMember(long userId, long projectId, int accessLevel);
}
