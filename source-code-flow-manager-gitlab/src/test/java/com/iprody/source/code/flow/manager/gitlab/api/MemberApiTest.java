package com.iprody.source.code.flow.manager.gitlab.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody.source.code.flow.manager.gitlab.api.member.AccessLevel;
import com.iprody.source.code.flow.manager.gitlab.api.member.Member;
import com.iprody.source.code.flow.manager.gitlab.api.member.MemberApi;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SoftAssertionsExtension.class)
public final class MemberApiTest {

    private static final String HEADER_TOKEN = "PRIVATE-TOKEN";
    private static final String TOKEN = "token";
    private static final String ERROR_MESSAGE_ADD_MEMBER = "Something went wrong during Gitlab operation "
            + "[add member] execution.";
    private static final String NAME_OF_MEMBER = "Raymond Smith";
    private static final int USER_ID = 1;
    private static final int PROJECT_ID = 1;
    private static final int INEXISTING_PROJECT = -1;
    private static final int INEXISTING_USER = -1;
    private MockWebServer mockWebServer;
    private MemberApi memberApi;


    @BeforeEach
    @SneakyThrows
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        final String baseUrl = mockWebServer.url("/").url().toString();
        final WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HEADER_TOKEN, TOKEN)
                .build();

        memberApi = new MemberApi(client);
    }

    @AfterEach
    @SneakyThrows
    void tearDown() {
        mockWebServer.shutdown();
    }

    @Test
    @SneakyThrows
    void shouldAddMemberToTheProject_onBehalfOfUser_thatEncodedInPrivateToken(SoftAssertions softly) {
        final int status = 200;
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(new ObjectMapper().writeValueAsString(getTestMember()))
        );

        final Mono<Member> memberMono = memberApi.addProjectMember(USER_ID, PROJECT_ID, AccessLevel.MINIMAL_ACCESS);

        StepVerifier.create(memberMono)
                .expectNextMatches(member -> member.getName().equals(NAME_OF_MEMBER))
                .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();

        softly.assertThat(recordedRequest)
                .returns("POST", RecordedRequest::getMethod)
                .returns("/projects/1/members?user_id=1&access_level=5", RecordedRequest::getPath);
        softly.assertThat(recordedRequest.getHeader(HEADER_TOKEN)).isEqualTo(TOKEN);
        softly.assertAll();
    }

    @Test
    @SneakyThrows
    void shouldNotAddMemberToTheProject_becauseOfWrongAuthToken_soResponseIs401() {
        final int status = 401;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.UNAUTHORIZED))
        );
        final Mono<Member> memberMono = memberApi.addProjectMember(USER_ID, PROJECT_ID, AccessLevel.MINIMAL_ACCESS);

        StepVerifier.create(memberMono)
                .expectErrorMatches(throwable -> throwable instanceof GitlabApiException
                        && throwable.getMessage().equals(ERROR_MESSAGE_ADD_MEMBER))
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotAddMemberToTheProject_becauseProjectNotFound_soResponseIs404() {
        final int status = 404;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.NOT_FOUND))
        );
        final Mono<Member> memberMono = memberApi
                .addProjectMember(USER_ID, INEXISTING_PROJECT, AccessLevel.MINIMAL_ACCESS);

        StepVerifier.create(memberMono)
                .expectErrorMatches(throwable -> throwable instanceof GitlabApiException
                        && throwable.getMessage().equals(ERROR_MESSAGE_ADD_MEMBER))
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotAddMemberToTheProject_becauseUserNotFound_soResponseIs404() {
        final int status = 404;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.NOT_FOUND))
        );
        final Mono<Member> memberMono = memberApi
                .addProjectMember(INEXISTING_USER, PROJECT_ID, AccessLevel.MINIMAL_ACCESS);

        StepVerifier.create(memberMono)
                .expectErrorMatches(throwable -> throwable instanceof GitlabApiException
                        && throwable.getMessage().equals(ERROR_MESSAGE_ADD_MEMBER))
                .verify();
    }

    @Test
    @SneakyThrows
    void shouldNotAddMemberToTheProject_becauseInternalServerError_soResponseIs500() {
        final int status = 500;

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(status)
                        .setBody(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
        );

        final Mono<Member> memberMono = memberApi.addProjectMember(USER_ID, PROJECT_ID, AccessLevel.MINIMAL_ACCESS);

        StepVerifier.create(memberMono)
                .expectErrorMatches(throwable -> throwable instanceof GitlabApiException
                        && throwable.getMessage().equals(ERROR_MESSAGE_ADD_MEMBER))
                .verify();
    }

    private Member getTestMember() {
        final Member member = new Member();
        member.setName(NAME_OF_MEMBER);
        return member;
    }
}
