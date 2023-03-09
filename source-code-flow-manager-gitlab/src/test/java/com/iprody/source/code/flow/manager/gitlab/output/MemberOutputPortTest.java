package com.iprody.source.code.flow.manager.gitlab.output;

import com.iprody.source.code.flow.manager.core.member.GitMember;
import com.iprody.source.code.flow.manager.gitlab.api.member.AccessLevel;
import com.iprody.source.code.flow.manager.gitlab.api.member.CreatedBy;
import com.iprody.source.code.flow.manager.gitlab.api.member.Member;
import com.iprody.source.code.flow.manager.gitlab.api.member.MemberApi;
import com.iprody.source.code.flow.manager.gitlab.exception.GitlabApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
public final class MemberOutputPortTest {
    private static final long USER_ID = 1;
    private static final long INEXISTING_USER = -1;
    private static final long PROJECT_ID = 1;
    private static final Instant INSTANT = Instant.now();
    private static final String ERROR_MESSAGE_ADD_MEMBER = "Something went wrong during Gitlab operation "
            + "[add member] execution. Response: ";
    private MemberOutputPort memberOutputPort;
    @Mock
    private MemberApi mockMemberApi;

    @BeforeEach
    void setUpMemberOutputPort() {
        memberOutputPort = new MemberOutputPort(mockMemberApi);
    }

    @Test
    void shouldAddMemberToTheProject_thenReturnMonoMember() {
        Mockito.when(mockMemberApi.addProjectMember(USER_ID, PROJECT_ID, AccessLevel.REPORTER))
                .thenReturn(Mono.just(getMember()));

        final Mono<GitMember> gitMemberMono = memberOutputPort
                .addMember(USER_ID, PROJECT_ID, AccessLevel.REPORTER.getLevelValue());

        StepVerifier.create(gitMemberMono)
                .expectNextMatches(gitMember -> getMember().getUsername().equals(gitMember.getUsername()))
                .verifyComplete();
    }

    @Test
    void shouldNotAddMemberToTheProject_becauseUserIdIsWrong_thenReturnError() {
        Mockito.when(mockMemberApi.addProjectMember(INEXISTING_USER, PROJECT_ID, AccessLevel.REPORTER))
                .thenReturn(Mono.error(new GitlabApiException(ERROR_MESSAGE_ADD_MEMBER + HttpStatus.NOT_FOUND)));

        final Mono<GitMember> gitMemberMono = memberOutputPort
                .addMember(INEXISTING_USER, PROJECT_ID, AccessLevel.REPORTER.getLevelValue());

        StepVerifier.create(gitMemberMono)
                .expectError(GitlabApiException.class)
                .verify();
    }

    private Member getMember() {
        final Member member = new Member();
        member.setId((int) USER_ID);
        member.setUsername("username");
        member.setName("name");
        member.setState("active");
        member.setAvatarUrl("https://www.gravatar.com/avatar/c2525a");
        member.setWebUrl("http://192.168.1.8:3000/root");
        member.setCreatedAt(INSTANT.toString());
        member.setExpiresAt(INSTANT.toString());
        member.setAccessLevel(AccessLevel.REPORTER.getLevelValue());
        member.setEmail("test@mail.com");
        member.setCreatedBy(new CreatedBy());
        return member;
    }
}
