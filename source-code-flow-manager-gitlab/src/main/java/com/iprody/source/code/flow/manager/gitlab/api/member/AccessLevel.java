package com.iprody.source.code.flow.manager.gitlab.api.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum AccessLevel {

    /**
     * Lack of access for the member.
     */
    NO_ACCESS(0),
    /**
     * Minimal access for the member.
     */
    MINIMAL_ACCESS(5),
    /**
     * Guest access for the member.
     */
    GUEST(10),
    /**
     * Reporter access for the member.
     */
    REPORTER(20),
    /**
     * Developer access for the member.
     */
    DEVELOPER(30),
    /**
     * Maintainer access for the member.
     */
    MAINTAINER(40),
    /**
     * Owner access for the member.
     */
    OWNER(50);
    /**
     * Represents level of access.
     */
    private final int levelValue;

    /**
     * Return the AccessLevel enum value that has the same levelValue as the value passed in, or throw a
     * NoSuchElementException if no such value exists.
     *
     * @param value The value of the enum constant to be returned.
     * @return A stream of AccessLevel objects
     */
    public static AccessLevel valueOf(int value) {
        return Arrays.stream(values())
                .filter(access -> access.levelValue == value)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No access level of Gitlab project found with value: "
                        + value));
    }
}
